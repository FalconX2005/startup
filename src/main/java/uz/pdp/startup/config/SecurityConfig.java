//
//package uz.pdp.startup.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
//import uz.pdp.startup.security.AuthService;
//import uz.pdp.startup.security.SecurityFilter;
//
//import static org.springframework.security.config.Customizer.withDefaults;
//
//@EnableWebSecurity
//@Configuration
//@EnableMethodSecurity
//public class SecurityConfig {
//
//    private final AuthService authService;
//    private final SecurityFilter securityFilter;
//
//    public SecurityConfig(@Lazy AuthService authService, @Lazy SecurityFilter securityFilter) {
//        this.authService = authService;
//        this.securityFilter = securityFilter;
//    }
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http.csrf(AbstractHttpConfigurer::disable);
//
//        // ✅ CorsConfig dagi sozlamalarni ishlatadi
//        http.cors(withDefaults());
//
//        http.userDetailsService(authService);
//
//        http.authorizeHttpRequests(conf -> conf
//                .requestMatchers(
//                        "/swagger-ui/**",
//                        "/v3/api-docs/**",
//                        "/swagger-resources/**",
//                        "/webjars/**",
//                        "/auth/**"
//                ).permitAll()
//                .anyRequest()
//                .authenticated()
//        );
//
//        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
//        return http.build();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(authService);
//        authProvider.setPasswordEncoder(passwordEncoder());
//        return authProvider;
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//}
//
package uz.pdp.startup.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import uz.pdp.startup.security.AuthService;
import uz.pdp.startup.security.SecurityFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebSecurity
@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    private final AuthService authService;
    private final SecurityFilter securityFilter;

    public SecurityConfig(@Lazy AuthService authService, @Lazy SecurityFilter securityFilter) {
        this.authService = authService;
        this.securityFilter = securityFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        // ✅ CSRF o‘chirib qo‘yildi
        http.csrf(AbstractHttpConfigurer::disable);

        // ✅ CorsConfig dagi sozlamalarni ishlatadi
        http.cors(withDefaults());

        // ✅ auth service
        http.userDetailsService(authService);

        // ✅ ruxsat berilgan URL lar
        http.authorizeHttpRequests(conf -> conf
                .requestMatchers(
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        "/swagger-resources/**",
                        "/webjars/**",
                        "/auth/**"
                ).permitAll()
                // ✅ OPTIONS (preflight) ga ham ruxsat
                .requestMatchers(org.springframework.http.HttpMethod.OPTIONS, "/**").permitAll()
                // boshqa barcha requestlar uchun auth kerak
                .anyRequest().authenticated()
        );

        // ✅ custom security filter
        http.addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(authService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
