package com.pawsstore.shop.security;

import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

@Component
@RequiredArgsConstructor
public final class JwtHelper {

    private final JwtProperties properties;



    public String createToken(Map<String, Object> claims, String subject) {
        Date expiryDate =
                Date.from(Instant.ofEpochMilli(System.currentTimeMillis() + properties.validity()));
        byte[] keyBytes = Base64.getDecoder().decode(properties.secretKey());
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(Date.from(Instant.ofEpochMilli(System.currentTimeMillis())))
                .expiration(expiryDate)
                .signWith(key)
                .compact();
    }

    public String extractUsername(String jwt) {

        byte[] keyBytes = Base64.getDecoder().decode(properties.secretKey());
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.parser().verifyWith(key)
                .build().parseSignedClaims(jwt).getPayload().getSubject();
    }

    public Date extractExpirationTime(String jwt) {

        byte[] keyBytes = Base64.getDecoder().decode(properties.secretKey());
        SecretKey key = Keys.hmacShaKeyFor(keyBytes);

        return Jwts.parser().verifyWith(key)
                .build().parseSignedClaims(jwt).getPayload().getExpiration();
    }

    public boolean validateToken(String token, String username) {
        try {

            if(Date.from(Instant.ofEpochMilli(System.currentTimeMillis())).after(extractExpirationTime(token)) &&
            username.equals(extractUsername(token))) {
                return false;
            }

            byte[] keyBytes = Base64.getDecoder().decode(properties.secretKey());
            SecretKey key = Keys.hmacShaKeyFor(keyBytes);

            Jwts.parser()
                    .verifyWith(key)
                    .build()
                    .parseSignedClaims(token);
            return true;
        } catch (JwtException | IllegalArgumentException exception) {
            return false;
        }
    }

}
