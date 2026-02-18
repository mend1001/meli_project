package com.marketplace.shared;

import java.time.Instant;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ApiError", description = "Estructura estándar de error")
public record ApiError(
        @Schema(example = "2026-02-17T12:34:56Z") String timestamp,
        @Schema(example = "404") int status,
        @Schema(example = "NOT_FOUND") String error,
        @Schema(example = "Product not found: MCO123") String message,
        @Schema(example = "/api/products/MCO123") String path
) {}

