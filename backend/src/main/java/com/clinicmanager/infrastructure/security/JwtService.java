package com.clinicmanager.infrastructure.security;

import com.clinicmanager.application.port.output.security.TokenServicePort;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;
import java.security.Key;
import java.util.Date;

@Service
public class JwtService implements TokenServicePort {

    // A secret key of at least 256 bits (32 bytes)
    private static final String SECRET_STRING = "clinicmanagersystemsupersecretkeyforjwtsigningandtokengenerationpurposes";
    private final Key signingKey = Keys.hmacShaKeyFor(SECRET_STRING.getBytes());

    // Access token: 1 hour (3,600,000 ms)
    private static final long ACCESS_TOKEN_EXPIRATION = 3600000;
    // Refresh token: 7 days (604,800,000 ms)
    private static final long REFRESH_TOKEN_EXPIRATION = 604800000;

    @Override
    public String generateAccessToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + ACCESS_TOKEN_EXPIRATION))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String generateRefreshToken(String username, String role) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + REFRESH_TOKEN_EXPIRATION))
                .signWith(signingKey, SignatureAlgorithm.HS256)
                .compact();
    }

    @Override
    public String getUsernameFromToken(String token) {
        return getClaims(token).getSubject();
    }

    @Override
    public String getRoleFromToken(String token) {
        return getClaims(token).get("role", String.class);
    }

    @Override
    public boolean validateToken(String token) {
        try {
            Claims claims = getClaims(token);
            return !claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getClaims(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(signingKey)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}
