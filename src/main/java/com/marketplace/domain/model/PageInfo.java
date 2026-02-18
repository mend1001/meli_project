package com.marketplace.domain.model;

public record PageInfo(
        int number,
        int size,
        long totalItems,
        int totalPages,
        boolean hasNext,
        boolean hasPrev
) {}

