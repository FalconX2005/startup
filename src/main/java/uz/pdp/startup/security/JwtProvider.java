package uz.pdp.startup.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import uz.pdp.startup.entity.User;
import uz.pdp.startup.exception.RestException;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.key}")
    private String jwtSecretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * JWT yaratish
     */
    public String generateToken(User user, Date expiration) {
        return Jwts.builder()
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole().name())
                .claim("companyId", user.getCompany() != null ? user.getCompany().getId() : null)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .signWith(getSigningKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Tokenni tekshirish va Claims qaytarish
     */
    public Claims validateAndGetClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw RestException.restThrow("Token muddati tugagan", HttpStatus.UNAUTHORIZED);
        } catch (UnsupportedJwtException e) {
            throw RestException.restThrow("Qo‘llab-quvvatlanmaydigan token", HttpStatus.BAD_REQUEST);
        } catch (MalformedJwtException e) {
            throw RestException.restThrow("Token formatida xatolik", HttpStatus.BAD_REQUEST);
        } catch (SecurityException e) {
            throw RestException.restThrow("Token imzosi noto‘g‘ri", HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            throw RestException.restThrow("Token noto‘g‘ri", HttpStatus.UNAUTHORIZED);
        }
    }

    /**
     * Username olish (token dan)
     */
    public String extractUsername(String token) {
        try {
            return validateAndGetClaims(token).getSubject();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Token validligini tekshirish
     */
    public boolean validateToken(String token, org.springframework.security.core.userdetails.UserDetails userDetails) {
        final String username = extractUsername(token);
        if (username == null) return false;
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }

    /**
     * Token muddati o‘tganligini tekshirish
     */
    private boolean isTokenExpired(String token) {
        try {
            final Date expiration = validateAndGetClaims(token).getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException e) {
            return true;
        }
    }
}
