package com.marketplace.domain.port.out;

import com.marketplace.domain.model.ProductCard;

import java.util.List;
import java.util.Optional;

public interface ProductCatalogRepository {
    List<ProductCard> findAll();
    Optional<ProductCard> findByItemId(String itemId);
}

