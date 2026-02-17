package com.marketplace.domain.model;

public class Product {

    private final String itemId;
    private final String productId;
    private final String title;
    private final String state;
    private final int availableQuantity;
    private final String sellerId;
    private final String sellerName;

    public Product(String itemId, String productId, String title, String state,
                   int availableQuantity, String sellerId, String sellerName) {
        this.itemId = itemId;
        this.productId = productId;
        this.title = title;
        this.state = state;
        this.availableQuantity = availableQuantity;
        this.sellerId = sellerId;
        this.sellerName = sellerName;
    }

    public String getItemId() { return itemId; }
    public String getProductId() { return productId; }
    public String getTitle() { return title; }
    public String getState() { return state; }
    public int getAvailableQuantity() { return availableQuantity; }
    public String getSellerId() { return sellerId; }
    public String getSellerName() { return sellerName; }
}

