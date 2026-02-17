package com.marketplace.infrastructure.adapter.in.rest;

import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.model.ProductDetailResponse;
import com.marketplace.domain.port.in.GetProductDetailUseCase;
import com.marketplace.domain.port.in.GetProductListUseCase;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/mp/products")
public class ProductController {

    private final GetProductListUseCase listUseCase;
    private final GetProductDetailUseCase detailUseCase;

    public ProductController(GetProductListUseCase listUseCase,
                             GetProductDetailUseCase detailUseCase) {
        this.listUseCase = listUseCase;
        this.detailUseCase = detailUseCase;
    }

    @GetMapping
    public List<ProductCard> list() {
        return listUseCase.getProductList();
    }

    @GetMapping("/{id}")
    public ProductDetailResponse detail(@PathVariable String id) {
        return detailUseCase.getDetail(id);
    }

}

