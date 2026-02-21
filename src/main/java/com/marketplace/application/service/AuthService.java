package com.marketplace.application.service;

import com.marketplace.domain.exception.BadResourceRequestException;
import com.marketplace.domain.exception.NoSuchResourceFoundException;
import com.marketplace.domain.exception.UnauthorizedException;
import com.marketplace.domain.port.in.AuthUseCase;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Service
public class AuthService implements AuthUseCase {

    private final Key key;
    private final long expirationMinutes;

    private final String demoUser;
    private final String demoPass;

    public AuthService(@Value("${security.jwt.secret}") String secret,
                       @Value("${security.jwt.expiration-minutes}") long expirationMinutes,
                       @Value("${security.auth.username}") String demoUser,
                       @Value("${security.auth.password}") String demoPass) {

        // clave HMAC segura (requiere >= 32 bytes aprox)
        this.key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
        this.expirationMinutes = expirationMinutes;
        this.demoUser = demoUser;
        this.demoPass = demoPass;
    }

    @Override
    public String issueToken(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new BadResourceRequestException("username/password are required");
        }

        // Para la prueba: auth simple
        if (!demoUser.equals(username) || !demoPass.equals(password)) {
            throw new UnauthorizedException("Invalid credentials");
        }

        Instant now = Instant.now();
        Instant exp = now.plusSeconds(expirationMinutes * 60);

        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(Date.from(now))
                .setExpiration(Date.from(exp))
                .claim("role", "USER")
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public long expiresInSeconds() {
        return expirationMinutes * 60;
    }

    public Key signingKey() {
        return key;
    }
}