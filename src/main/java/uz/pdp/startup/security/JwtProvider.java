package uz.pdp.startup.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import uz.pdp.startup.entity.User;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.key}")
    private String jwtSecretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateToken(User user, Date expiration) {
        return Jwts.builder()
                .signWith(getSigningKey())
                .setSubject(user.getUsername())
                .claim("id", user.getId())
                .claim("role", user.getRole().name())
                .claim("companyId", user.getCompany() != null ? user.getCompany().getId() : null) // companyId
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .compact();
    }

    public Claims validateAndGetClaims(String token) {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token muddati tugagan");
        } catch (Exception e) {
            throw new RuntimeException("Token noto‘g‘ri");
        }
    }
}
