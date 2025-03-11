package com.java.pabloescobanks.security;

import com.java.pabloescobanks.exception.JwtMissingException;
import com.java.pabloescobanks.exception.JwtValidationException;
import com.java.pabloescobanks.util.JwtUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class JwtFilter extends OncePerRequestFilter {
    private static final Logger logger = LoggerFactory.getLogger(JwtFilter.class);
    private final JwtUtil jwtUtil;

    public JwtFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        boolean shouldSkip = path.startsWith("/auth");
        logger.info("🛑 Skipping JWT filter for request path: {}? {}", path, shouldSkip);
        return shouldSkip; // ✅ Skip JWT filtering for /auth endpoints
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");

        if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer ")) {
            throw new JwtMissingException("Authorization header is missing or invalid");
        }

        String token = authorizationHeader.substring(7);
        logger.info("🔐 Extracted Token: {}", token);

        String username = jwtUtil.extractUsername(token);
        String role = jwtUtil.extractRole(token);

        logger.info("👤 Extracted Username: {}", username);
        logger.info("👤 Extracted roles: {}", role);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            if (!jwtUtil.validateToken(token, username)) {
                throw new JwtValidationException("Token validation failed for user: " + username);
            }

            logger.info("✅ Token is valid for user: {}", username);
            logger.info("📌 Assigning roles: {}", role);

            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role);

            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(username, null, Collections.singletonList(authority));

            SecurityContextHolder.getContext().setAuthentication(authToken);
        }

        filterChain.doFilter(request, response);
    }
}
