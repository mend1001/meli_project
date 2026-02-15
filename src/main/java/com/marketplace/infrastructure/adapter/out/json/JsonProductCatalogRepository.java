package com.marketplace.infrastructure.adapter.out.json;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.port.out.ProductCatalogRepository;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.InputStream;
import java.util.*;

@Repository
public class JsonProductCatalogRepository implements ProductCatalogRepository {

    private static final String CATALOG_PATH = "data/catalog/products_nosql_polycards.json";

    private final ObjectMapper objectMapper;

    // cache simple en memoria para no leer el archivo en cada request
    private volatile List<ProductCard> cache;

    public JsonProductCatalogRepository(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    @Override
    public List<ProductCard> findAll() {
        ensureLoaded();
        return cache;
    }

    @Override
    public Optional<ProductCard> findByItemId(String itemId) {
        ensureLoaded();
        return cache.stream().filter(p -> Objects.equals(p.itemId(), itemId)).findFirst();
    }

    private void ensureLoaded() {
        if (cache != null) return;

        synchronized (this) {
            if (cache != null) return;
            cache = Collections.unmodifiableList(loadFromJson());
        }
    }

    private List<ProductCard> loadFromJson() {
        try (InputStream is = new ClassPathResource(CATALOG_PATH).getInputStream()) {

            JsonNode root = objectMapper.readTree(is);
            if (!root.isArray()) return List.of();

            List<ProductCard> result = new ArrayList<>();
            for (JsonNode node : root) {
                JsonNode poly = node.path("polycard");
                JsonNode meta = poly.path("metadata");

                String itemId = meta.path("id").asText(null);
                String productId = meta.path("product_id").asText(null);

                // components parsing
                String title = null;
                Integer priceValue = null;
                String currency = null;
                Boolean freeShipping = null;
                String badgeText = null;
                Double ratingValue = null;
                String soldLabel = null;
                Map<String, String> attrs = new LinkedHashMap<>();

                for (JsonNode comp : poly.path("components")) {
                    String type = comp.path("type").asText("");

                    switch (type) {
                        case "title" -> title = comp.path("title").path("text").asText(null);

                        case "price" -> {
                            JsonNode current = comp.path("price").path("current_price");
                            if (current.has("value")) priceValue = current.path("value").isNumber() ? current.path("value").asInt() : null;
                            currency = current.path("currency").asText(null);
                        }

                        case "shipping" -> {
                            JsonNode shipping = comp.path("shipping");
                            if (shipping.has("free")) freeShipping = shipping.path("free").asBoolean();
                            else freeShipping = "Envío gratis".equalsIgnoreCase(shipping.path("text").asText(""));
                        }

                        case "highlight" -> badgeText = comp.path("highlight").path("text").asText(null);

                        case "review_compacted" -> {
                            JsonNode rc = comp.path("review_compacted");
                            // soporta tu estructura mejorada: rating.value + sold.label
                            if (rc.has("rating")) {
                                ratingValue = rc.path("rating").path("value").isNumber() ? rc.path("rating").path("value").asDouble() : null;
                            } else {
                                // fallback si viene en values
                                // no forzamos: dejamos null si no existe
                            }
                            if (rc.has("sold")) {
                                soldLabel = rc.path("sold").path("label").asText(null);
                            }
                        }

                        case "attributes" -> {
                            for (JsonNode a : comp.path("attributes")) {
                                String name = a.path("name").asText(null);
                                String value = a.path("value").asText(null);
                                if (name != null && value != null) attrs.put(name, value);
                            }
                        }
                    }
                }

                // picture id (primer picture)
                String pictureId = null;
                JsonNode pictures = poly.path("pictures").path("pictures");
                if (pictures.isArray() && pictures.size() > 0) {
                    pictureId = pictures.get(0).path("id").asText(null);
                }

                // si no hay title en components, fallback a null
                result.add(new ProductCard(
                        itemId, productId, title,
                        priceValue, currency,
                        freeShipping,
                        pictureId,
                        badgeText,
                        ratingValue,
                        soldLabel,
                        attrs.isEmpty() ? Map.of() : Collections.unmodifiableMap(attrs)
                ));
            }
            return result;

        } catch (Exception e) {
            throw new IllegalStateException("Failed to load catalog JSON from classpath:" + CATALOG_PATH, e);
        }
    }
}
