package com.marketplace.infrastructure.adapter.out.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.domain.model.HighlightTemplate;
import com.marketplace.domain.model.ReviewTemplate;
import com.marketplace.domain.model.UiTemplates;
import com.marketplace.domain.port.out.UiTemplateRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Repository
public class JsonUiTemplateRepository implements UiTemplateRepository {

    private static final String PATH = "data/catalog/highlight_review_structures.json";

    private final ObjectMapper objectMapper;
    private volatile UiTemplates cache;

    public JsonUiTemplateRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public UiTemplates loadTemplates() {
        if (cache != null) return cache;
        synchronized (this) {
            if (cache != null) return cache;
            cache = read();
            return cache;
        }
    }

    private UiTemplates read() {
        try (InputStream is = new ClassPathResource(PATH).getInputStream()) {

            JsonNode root = objectMapper.readTree(is);

            List<HighlightTemplate> highlights = new ArrayList<>();
            for (JsonNode h : root.path("highlight_examples")) {
                JsonNode highlight = h.path("highlight");
                JsonNode styles = highlight.path("styles");

                highlights.add(new HighlightTemplate(
                        highlight.path("text").asText(null),
                        highlight.path("priority").isNumber() ? highlight.path("priority").asInt() : null,
                        highlight.path("variant").asText(null),
                        styles.path("color_hex").asText(null),
                        styles.path("background_color_hex").asText(null)
                ));
            }

            List<ReviewTemplate> reviews = new ArrayList<>();
            for (JsonNode r : root.path("review_compacted_examples")) {
                JsonNode rc = r.path("review_compacted");
                JsonNode rating = rc.path("rating");
                JsonNode sold = rc.path("sold");

                reviews.add(new ReviewTemplate(
                        rating.path("value").asDouble(),
                        rating.path("max").asInt(5),
                        rating.path("count").asInt(0),
                        sold.path("quantity").asInt(0),
                        sold.path("label").asText(null),
                        rc.path("alt_text").asText(null)
                ));
            }

            return new UiTemplates(List.copyOf(highlights), List.copyOf(reviews));

        } catch (Exception e) {
            throw new IllegalStateException("Failed to load UI templates JSON from classpath:" + PATH, e);
        }
    }
}

