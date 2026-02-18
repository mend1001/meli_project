package com.marketplace.application.service;

import com.marketplace.domain.exception.BadResourceRequestException;
import com.marketplace.domain.model.PageInfo;
import com.marketplace.domain.model.PagedResponse;
import com.marketplace.domain.model.ProductCard;
import com.marketplace.domain.port.in.GetProductListUseCase;
import com.marketplace.domain.port.out.ProductCatalogRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GetProductListService implements GetProductListUseCase {

    private final ProductCatalogRepository jsonRepo;

    public GetProductListService(ProductCatalogRepository jsonRepo) {
        this.jsonRepo = jsonRepo;
    }

    @Override
    public PagedResponse<ProductCard> getProductList(int page, int size) {

        if (page < 0) {
            throw new BadResourceRequestException("Invalid 'page'. Must be >= 0");
        }
        if (size <= 0) {
            throw new BadResourceRequestException("Invalid 'size'. Must be >= 1");
        }
        if (size > 50) {
            throw new BadResourceRequestException("Invalid 'size'. Must be <= 50");
        }

        List<ProductCard> all = jsonRepo.findAll();
        int totalItems = all.size();

        if (totalItems == 0) {
            PageInfo pageInfo = new PageInfo(page, size, 0, 0, false, page > 0);
            return new PagedResponse<>(List.of(), pageInfo);
        }

        int totalPages = (int) Math.ceil((double) totalItems / (double) size);
        int fromIndex = page * size;

        if (fromIndex >= totalItems) {
            PageInfo pageInfo = new PageInfo(page, size, totalItems, totalPages, false, page > 0);
            return new PagedResponse<>(List.of(), pageInfo);
        }

        int toIndex = Math.min(fromIndex + size, totalItems);
        List<ProductCard> slice = all.subList(fromIndex, toIndex);

        boolean hasPrev = page > 0;
        boolean hasNext = (page + 1) < totalPages;

        PageInfo pageInfo = new PageInfo(
                page,
                size,
                totalItems,
                totalPages,
                hasNext,
                hasPrev
        );

        return new PagedResponse<>(slice, pageInfo);
    }
}



