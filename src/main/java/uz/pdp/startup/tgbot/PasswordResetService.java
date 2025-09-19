package uz.pdp.startup.tgbot;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.startup.entity.User;
import uz.pdp.startup.repository.UserRepository;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    private final Map<String, CodeInfo> resetCodeMap = new ConcurrentHashMap<>();
    private final Map<String, Long> userChatMap = new ConcurrentHashMap<>();

    private static final long EXPIRATION_TIME_MS = 5 * 60 * 1000;

    public String startReset(String username, Long chatId) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi"));

        String code = generateCode();
        long now = System.currentTimeMillis();
        resetCodeMap.put(username, new CodeInfo(code, now));
        userChatMap.put(username, chatId);
        return code;
    }

    public Long updatePassword(String username, String code, String newPassword) {
        CodeInfo info = resetCodeMap.get(username);
        if (info == null || !info.code.equals(code)) {
            throw new IllegalArgumentException("Kod noto‘g‘ri yoki topilmadi");
        }

        long now = System.currentTimeMillis();
        if (now - info.timestamp > EXPIRATION_TIME_MS) {
            resetCodeMap.remove(username);
            userChatMap.remove(username);
            throw new IllegalArgumentException("Kod eskirgan, qayta urining");
        }

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi"));

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);

        resetCodeMap.remove(username);
        return userChatMap.remove(username);
    }

    private String generateCode() {
        return String.valueOf(1000 + ThreadLocalRandom.current().nextInt(9000));
    }

    private static class CodeInfo {
        final String code;
        final long timestamp;

        CodeInfo(String code, long timestamp) {
            this.code = code;
            this.timestamp = timestamp;
        }
    }
}
