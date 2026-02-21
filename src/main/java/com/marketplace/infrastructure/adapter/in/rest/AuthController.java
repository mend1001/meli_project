package com.marketplace.infrastructure.adapter.in.rest;

import com.marketplace.application.service.AuthService;
import com.marketplace.infrastructure.adapter.in.rest.dto.AuthRequest;
import com.marketplace.infrastructure.adapter.in.rest.dto.AuthResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mp/auth")
@Tag(name = "Auth", description = "Autenticación y emisión de token JWT")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/token")
    public ResponseEntity<AuthResponse> token(@RequestBody AuthRequest req) {
        String jwt = authService.issueToken(req.username(), req.password());
        return ResponseEntity.ok(new AuthResponse(jwt, "Bearer", authService.expiresInSeconds()));
    }
}
