package uz.pdp.startup.security;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.pdp.startup.entity.User;
import uz.pdp.startup.enums.RoleEnum;
import uz.pdp.startup.payload.LoginDTO;
import uz.pdp.startup.payload.RegisterDTO;
import uz.pdp.startup.repository.UserRepository;

import java.util.Date;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Lazy
    private final JwtProvider jwtProvider;

    @Value("${app.admin.username}")
    private String fallbackUsername;

    @Value("${app.admin.password}")
    private String fallbackPassword;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.getUserByUsername(username)
                .map(user -> (UserDetails) user)
                .orElseGet(() -> {
                    if (username.equals(fallbackUsername)) {
                        User admin = new User();
                        admin.setUsername(fallbackUsername);
                        admin.setPassword(passwordEncoder.encode(fallbackPassword));
                        admin.setRole(RoleEnum.ADMIN);
                        return admin;
                    }
                    throw new UsernameNotFoundException("Foydalanuvchi topilmadi: " + username);
                });
    }

    @Override
    public String login(LoginDTO loginDTO) {
        User user = (User) loadUserByUsername(loginDTO.getUsername());

        boolean matches = passwordEncoder.matches(loginDTO.getPassword(), user.getPassword());
        if (!matches) {
            throw new AccessDeniedException("Username yoki parol noto‘g‘ri");
        }

        return jwtProvider.generateToken(
                user.getUsername(),
                new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000) // 1 kunlik token
        );
    }

    @Override
    public String register(RegisterDTO registerDTO) {
        if (userRepository.existsByUsername(registerDTO.getUsername())) {
            throw new RuntimeException("Bu username allaqachon mavjud");
        }

        User user = new User();
        user.setUsername(registerDTO.getUsername());
        user.setPassword(passwordEncoder.encode(registerDTO.getPassword()));
//        user.setEmail(registerDTO.getEmail());
        user.setRole(registerDTO.getRole());

        userRepository.save(user);

        return "Foydalanuvchi muvaffaqiyatli ro'yxatdan o'tdi";
    }
}
