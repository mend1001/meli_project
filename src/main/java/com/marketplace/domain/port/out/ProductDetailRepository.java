package com.marketplace.domain.port.out;

import com.marketplace.domain.model.Product;
import java.util.Optional;

public interface ProductDetailRepository {
    Optional<Product> findByItemId(String itemId);
}

