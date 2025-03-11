package com.java.pabloescobanks.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

@Component
public class JwtUtil {
    private final String SECRET_KEY = "3458934898923840238042482384jkdfgkdfkgjdfkjgrdr9898890";

    public String generateToken(String username, String role) {
        return Jwts.builder()
                .subject(username)  // (1) Stores username in the "sub" (subject) claim
                .claim("role", role)  // Store a single role as a claim
                .issuedAt(new Date())  // (3) Sets issued time
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60))  // (4) Sets expiration (1 hour)
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY)  // (5) Signs the token
                .compact();  // (6) Converts everything into a JWT string
    }


    public String extractUsername(String token) {
        return getClaims(token).getSubject();
    }

    public String extractRole(String token) {
        Claims claims = getClaims(token);
        return claims.get("role", String.class); // Extract role as a single string
    }

    public boolean validateToken(String token, String username) {
        return (username.equals(extractUsername(token)) && !isTokenExpired(token));
    }

    private Claims getClaims(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return getClaims(token).getExpiration().before(new Date());
    }

}
