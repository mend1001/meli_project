package com.marketplace.domain.model;

import java.util.Map;

public record ProductDetailResponse(
        String itemId,
        String productId,
        String title,
        String state,
        int availableQuantity,
        String sellerId,
        String sellerName,

        Integer priceValue,
        String currency,
        Boolean freeShipping,
        String pictureId,
        String badgeText,
        Double ratingValue,
        String soldLabel,
        Map<String, String> attributes
) {}

