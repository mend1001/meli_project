package com.marketplace.domain.port.in;

import com.marketplace.domain.model.ProductDetailResponse;

public interface GetProductDetailUseCase {
    ProductDetailResponse getDetail(String itemId);
}


