package com.marketplace.domain.model;

import java.util.List;

public record UiTemplates(
        List<HighlightTemplate> highlightExamples,
        List<ReviewTemplate> reviewCompactedExamples
) {}
