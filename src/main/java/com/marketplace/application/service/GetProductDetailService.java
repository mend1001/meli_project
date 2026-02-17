package com.marketplace.application.service;

import com.marketplace.domain.exception.NoSuchResourceFoundException;
import com.marketplace.domain.model.Product;
import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.model.ProductDetailResponse;
import com.marketplace.domain.port.in.GetProductDetailUseCase;
import com.marketplace.domain.port.out.ProductCatalogRepository;
import com.marketplace.domain.port.out.ProductDetailRepository;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class GetProductDetailService implements GetProductDetailUseCase {

    private final ProductDetailRepository sqliteRepo;
    private final ProductCatalogRepository jsonRepo;

    public GetProductDetailService(ProductDetailRepository sqliteRepo,
                                   ProductCatalogRepository jsonRepo) {
        this.sqliteRepo = sqliteRepo;
        this.jsonRepo = jsonRepo;
    }

    @Override
    public ProductDetailResponse getDetail(String itemId) {

        Product db = sqliteRepo.findByItemId(itemId)
                .orElseThrow(() -> new NoSuchResourceFoundException("Product not found: " + itemId));

        ProductCard ui = jsonRepo.findByItemId(itemId).orElse(null);

        return new ProductDetailResponse(
                db.getItemId(),
                db.getProductId(),
                db.getTitle(),
                db.getState(),
                db.getAvailableQuantity(),
                db.getSellerId(),
                db.getSellerName(),

                ui != null ? ui.priceValue() : null,
                ui != null ? ui.currency() : null,
                ui != null ? ui.freeShipping() : null,
                ui != null ? ui.pictureId() : null,
                ui != null ? ui.badgeText() : null,
                ui != null ? ui.ratingValue() : null,
                ui != null ? ui.soldLabel() : null,
                ui != null ? ui.attributes() : Map.of()
        );
    }
}


