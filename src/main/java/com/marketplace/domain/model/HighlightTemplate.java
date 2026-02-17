package com.marketplace.domain.model;

public record HighlightTemplate(
        String text,
        Integer priority,
        String variant,
        String colorHex,
        String backgroundColorHex
) {}
