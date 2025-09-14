package uz.pdp.startup.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import uz.pdp.startup.entity.User;

import java.io.IOException;
import java.util.Objects;

/**
 * Created by: Umar
 * DateTime: 7/19/2025 2:39 PM
 */
@Component
public class SecurityFilter  extends OncePerRequestFilter {

    private final JwtProvider jwtProvider;
    private final AuthService authService;

    public SecurityFilter(JwtProvider jwtProvider, @Lazy AuthService authService) {
        this.jwtProvider = jwtProvider;
        this.authService = authService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        String authorization = request.getHeader("Authorization");

        // todo JWT token
        if (Objects.nonNull(authorization) && authorization.startsWith("Bearer ")) {

            String token = authorization.substring(7);

            String username = jwtProvider.validateToken(token);

            User user = (User)authService.loadUserByUsername(username);

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        }



        filterChain.doFilter(request, response);
    }

}
