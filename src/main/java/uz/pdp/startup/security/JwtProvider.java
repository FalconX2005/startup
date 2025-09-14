package uz.pdp.startup.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Date;

/**
 * Created by: Umar
 * DateTime: 7/19/2025 2:41 PM
 */
@Component
public class JwtProvider {

    @Value("${jwt.key}")
    private String jwtSecretKey;

    public String generateToken(String username, Date expiration) {

        SecretKey secretKey = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());

        return Jwts.builder()
                .signWith(secretKey)
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(expiration)
                .compact();

    }

    public String validateToken(String token) {

        SecretKey key = Keys.hmacShaKeyFor(jwtSecretKey.getBytes());

        Jws<Claims> claimsJws = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);

        return claimsJws.getBody().getSubject();

    }

}
