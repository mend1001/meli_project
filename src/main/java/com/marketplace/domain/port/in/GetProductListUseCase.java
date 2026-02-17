package com.marketplace.domain.port.in;

import com.marketplace.domain.model.ProductCard;
import java.util.List;

public interface GetProductListUseCase {
    List<ProductCard> getProductList();
}

