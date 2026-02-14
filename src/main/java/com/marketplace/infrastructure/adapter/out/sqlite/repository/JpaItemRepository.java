package com.marketplace.infrastructure.adapter.out.sqlite.repository;

import com.marketplace.infrastructure.adapter.out.sqlite.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaItemRepository extends JpaRepository<ItemEntity, String> {
}

