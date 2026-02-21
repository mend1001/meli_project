package com.marketplace.infrastructure.adapter.in.rest.dto;

public record AuthResponse(String token, String tokenType, long expiresInSeconds) {}