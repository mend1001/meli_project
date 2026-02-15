package com.marketplace.domain.port.out;

import com.marketplace.domain.model.UiTemplates;

public interface UiTemplateRepository {
    UiTemplates loadTemplates();
}

