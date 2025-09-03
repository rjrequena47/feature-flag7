package com.bytes7.feature_flag7.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtils {

    private final Key key;

    // Se inyecta desde application.yml
    public JwtUtils(@Value("${spring.jwt.secret}") String secret) {
        if (secret.length() < 64) {
            throw new IllegalArgumentException("La clave JWT debe tener al menos 64 caracteres para HS512");
        }
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
    }

    // Genera token JWT
    public String generateToken(String username, String role) {
        long expirationMillis = 1000 * 60 * 60; // 1 hora
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + expirationMillis))
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // Valida token JWT
    public boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }
    }

    // Obtiene username desde token
    public String getUsernameFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }
}
