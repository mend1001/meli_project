package com.marketplace.infrastructure.config;

import com.marketplace.application.service.AuthService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class JwtAuthFilter extends GenericFilter {

    private final AuthService authService;

    public JwtAuthFilter(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        String header = req.getHeader("Authorization");

        if (header != null && header.startsWith("Bearer ")) {
            String token = header.substring("Bearer ".length());

            try {
                Claims claims = Jwts.parserBuilder()
                        .setSigningKey(authService.signingKey())
                        .build()
                        .parseClaimsJws(token)
                        .getBody();

                String user = claims.getSubject();
                String role = (String) claims.getOrDefault("role", "USER");

                var auth = new UsernamePasswordAuthenticationToken(
                        user,
                        null,
                        List.of(new SimpleGrantedAuthority("ROLE_" + role))
                );

                SecurityContextHolder.getContext().setAuthentication(auth);

            } catch (Exception ignored) {
                // Si el token es inválido/expiró, no autenticamos y dejamos que Security responda 401
                SecurityContextHolder.clearContext();
            }
        }

        chain.doFilter(request, response);
    }
}
