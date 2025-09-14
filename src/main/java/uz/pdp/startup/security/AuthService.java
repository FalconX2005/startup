package uz.pdp.startup.security;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import uz.pdp.startup.payload.LoginDTO;
import uz.pdp.startup.payload.RegisterDTO;

import java.nio.file.AccessDeniedException;
import java.util.Objects;

/**
 * Created by: Umar
 * DateTime: 7/19/2025 2:32 PM
 */
@Service
public interface AuthService extends UserDetailsService {
    Object login(LoginDTO loginDTO);

    String register(RegisterDTO registerDTO);

    String refreshToken(String refreshToken);
}
