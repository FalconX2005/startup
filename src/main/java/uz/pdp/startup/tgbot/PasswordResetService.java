package uz.pdp.startup.tgbot;//package uz.pdp.startupproject.tgbot;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.stereotype.Service;
//import uz.pdp.startupproject.entity.User;
//import uz.pdp.startupproject.repository.UserRepository;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Service
//@RequiredArgsConstructor
//public class PasswordResetService {
//
//    private final UserRepository userRepository;
//    private final PasswordEncoder passwordEncoder;
//
//    private final Map<String, CodeInfo> resetCodeMap = new HashMap<>();
//    private final Map<String, Long> userChatMap = new HashMap<>();
//
//    private static final long EXPIRATION_TIME = 5 * 60 * 1000;
//
//
//    public String startReset(String username, Long chatId) {
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi"));
//
//        String code = generateCode();
//        long now = System.currentTimeMillis();
//        resetCodeMap.put(username, new CodeInfo(code,now));
//        userChatMap.put(username, chatId);
//        return code;
//    }
//
//
//    public Long updatePassword(String username, String code, String newPassword) {
//        CodeInfo info = resetCodeMap.get(username);
//        if (info == null || !info.code.equals(code)) {
//            throw new IllegalArgumentException("Kod noto‘g‘ri yoki topilmadi");
//        }
//
//        long now = System.currentTimeMillis();
//        if (now - info.timestamp > EXPIRATION_TIME) {
//            resetCodeMap.remove(username);
//            userChatMap.remove(username);
//            throw new IllegalArgumentException("Kod eskirgan, qayta urining");
//        }
//
//        User user = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("Foydalanuvchi topilmadi"));
//
//        user.setPassword(passwordEncoder.encode(newPassword));
//        userRepository.save(user);
//
//        resetCodeMap.remove(username);
//        return userChatMap.remove(username);
//
//    }
//
//    private String generateCode() {
//        return String.valueOf((int)(Math.random() * 9000) + 1000);
//    }
//
//    private static class CodeInfo {
//        String code;
//        long timestamp;
//
//        public CodeInfo(String code, long timestamp) {
//            this.code = code;
//            this.timestamp = timestamp;
//        }
//    }
//}
//
