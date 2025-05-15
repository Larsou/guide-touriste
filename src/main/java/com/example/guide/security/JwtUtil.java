package com.example.guide.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Injection des propriétés
    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private long expirationMs;

    /**
     * Génère un token JWT pour un sujet (ici l'email de l'utilisateur).
     */
    public String generateToken(String subject) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expirationMs);

        Key key = Keys.hmacShaKeyFor(secret.getBytes());

        return Jwts.builder()
                .setSubject(subject)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    /**
     * Extrait le sujet (email) depuis un token JWT.
     */
    public String getSubjectFromToken(String token) {
        Key key = Keys.hmacShaKeyFor(secret.getBytes());
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    /**
     * Vérifie si le token est valide (signature et expiration).
     */
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(secret.getBytes());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException ex) {
            // SignatureException, ExpiredJwtException, etc.
            return false;
        }
    }
}
