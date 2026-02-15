package com.marketplace.domain.model;

public record ReviewTemplate(
        double ratingValue,
        int ratingMax,
        int ratingCount,
        int soldQuantity,
        String soldLabel,
        String altText
) {}

