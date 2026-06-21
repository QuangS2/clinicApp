package com.clinicmanager.application.port.output.security;

public interface TokenServicePort {
    String generateAccessToken(String username, String role);
    String generateRefreshToken(String username, String role);
    String getUsernameFromToken(String token);
    String getRoleFromToken(String token);
    boolean validateToken(String token);
}
