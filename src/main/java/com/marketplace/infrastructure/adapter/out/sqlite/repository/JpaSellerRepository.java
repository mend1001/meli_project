package com.marketplace.infrastructure.adapter.out.sqlite.repository;

import com.marketplace.infrastructure.adapter.out.sqlite.entity.SellerEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaSellerRepository extends JpaRepository<SellerEntity, String> {
}

