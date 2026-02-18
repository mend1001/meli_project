package com.marketplace.domain.port.in;

import com.marketplace.domain.model.PagedResponse;
import com.marketplace.domain.model.ProductCard;

public interface GetProductListUseCase {
    PagedResponse<ProductCard> getProductList(int page, int size);
}


