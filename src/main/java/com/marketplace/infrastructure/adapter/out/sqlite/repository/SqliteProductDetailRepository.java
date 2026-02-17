package com.marketplace.infrastructure.adapter.out.sqlite.repository;

import com.marketplace.domain.model.Product;
import com.marketplace.domain.port.out.ProductDetailRepository;
import com.marketplace.infrastructure.adapter.out.sqlite.entity.ItemEntity;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class SqliteProductDetailRepository implements ProductDetailRepository {

    private final JpaItemRepository itemRepository;

    public SqliteProductDetailRepository(JpaItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    @Override
    public Optional<Product> findByItemId(String itemId) {
        return itemRepository.findById(itemId).map(this::toDomain);
    }

    private Product toDomain(ItemEntity e) {
        return new Product(
                e.getItemId(),
                e.getProduct().getProductId(),
                e.getTitle(),
                e.getState(),
                e.getAvailableQuantity(),
                e.getSeller().getSellerId(),
                e.getSeller().getName()
        );
    }
}


