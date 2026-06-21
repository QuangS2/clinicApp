package com.clinicmanager.infrastructure.security;

import com.clinicmanager.application.port.output.security.TokenServicePort;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenServicePort tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String authHeader = request.getHeader("Authorization");
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7);
            if (tokenService.validateToken(token)) {
                String username = tokenService.getUsernameFromToken(token);
                String role = tokenService.getRoleFromToken(token);

                if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                    String authority = role.startsWith("ROLE_") ? role : "ROLE_" + role;
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                            username,
                            null,
                            List.of(new SimpleGrantedAuthority(authority))
                    );
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        filterChain.doFilter(request, response);
    }
}
