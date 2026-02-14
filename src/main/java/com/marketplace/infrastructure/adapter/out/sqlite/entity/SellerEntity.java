package com.marketplace.infrastructure.adapter.out.sqlite.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "seller")
public class SellerEntity {
    @Id
    @Column(name = "seller_id")
    private String sellerId;

    @Column(nullable = false)
    private String name;

    public String getSellerId() {
        return sellerId;
    }

    public void setSellerId(String sellerId) {
        this.sellerId = sellerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

