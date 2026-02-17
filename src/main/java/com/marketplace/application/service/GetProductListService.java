package com.marketplace.application.service;

import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.port.in.GetProductListUseCase;
import com.marketplace.domain.port.out.ProductCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductListService implements GetProductListUseCase {

    private final ProductCatalogRepository repository;

    public GetProductListService(ProductCatalogRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ProductCard> getProductList() {
        return repository.findAll();
    }
}

