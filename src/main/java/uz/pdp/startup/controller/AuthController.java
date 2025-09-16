package uz.pdp.startup.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.pdp.startup.payload.ApiResult;
import uz.pdp.startup.payload.LoginDTO;
import uz.pdp.startup.payload.RegisterDTO;
import uz.pdp.startup.security.AuthService;

/**
 * Created by: Umar
 * DateTime: 7/20/2025 4:59 PM
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public Object login(@RequestBody LoginDTO loginDTO) {
        return authService.login(loginDTO);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody RegisterDTO registerDTO) {
        String register = authService.register(registerDTO);
        return ResponseEntity.ok(register);
    }

    @PostMapping("/refresh")
    public ResponseEntity<ApiResult<String>> refresh(@RequestBody String refreshToken) {
        String newAccessToken = authService.refreshToken(refreshToken);
        return ResponseEntity.ok(ApiResult.success(newAccessToken));
    }
}
