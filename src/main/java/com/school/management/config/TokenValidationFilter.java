package com.school.management.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.filter.OncePerRequestFilter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class TokenValidationFilter extends OncePerRequestFilter {

    private static final Logger log = LoggerFactory.getLogger(TokenValidationFilter.class);
    private final RestTemplate restTemplate;
    private final String authServiceUrl;

    public TokenValidationFilter(RestTemplate restTemplate, @Value("${auth.service.url}") String authServiceUrl) {
        this.restTemplate = restTemplate;
        this.authServiceUrl = authServiceUrl;
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getRequestURI();
        return "OPTIONS".equalsIgnoreCase(request.getMethod()) || path.startsWith("/api/auth/");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        String header = request.getHeader("Authorization");
        if (header == null || !header.startsWith("Bearer ")) {
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Missing or invalid Authorization header\"}");
            return;
        }
        String token = header.substring(7);
        try {
            @SuppressWarnings("unchecked")
            Map<String, String> result = restTemplate.postForObject(
                    authServiceUrl + "/api/auth/validate",
                    Map.of("token", token),
                    Map.class);
            if (result == null || result.containsKey("error")) {
                throw new RuntimeException("Invalid token");
            }
            String username = result.get("username");
            String role = result.get("role");
            List<SimpleGrantedAuthority> authorities = List.of(new SimpleGrantedAuthority("ROLE_" + role));
            UsernamePasswordAuthenticationToken authentication =
                    new UsernamePasswordAuthenticationToken(username, null, authorities);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (HttpClientErrorException e) {
            System.out.println(e);
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Invalid or expired token\"}");
        } catch (Exception e) {
            log.error("Token validation error: {}", e.getMessage());
            response.setStatus(401);
            response.setContentType("application/json");
            response.getWriter().write("{\"error\":\"Authentication service unavailable\"}");
        }
    }
}
