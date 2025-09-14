package uz.pdp.startup.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

@Component
public class JwtProvider {

    @Value("${jwt.key}")
    private String jwtSecretKey;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes());
    }

    public String generateToken(String username, Date expiration) {
        return Jwts.builder()
                .signWith(getSigningKey())
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .compact();
    }

    public String validateToken(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parserBuilder()
                    .setSigningKey(getSigningKey())
                    .build()
                    .parseClaimsJws(token);

            return claimsJws.getBody().getSubject();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException("Token muddati tugagan");
        } catch (Exception e) {
            throw new RuntimeException("Token noto‘g‘ri");
        }
    }
}
