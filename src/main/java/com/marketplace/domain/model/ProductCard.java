package com.marketplace.domain.model;

import java.util.Map;

public record ProductCard(
        String itemId,
        String productId,
        String title,
        Integer priceValue,
        String currency,
        Boolean freeShipping,
        String pictureId,
        String badgeText,
        Double ratingValue,
        String soldLabel,
        Map<String, String> attributes
) {}

