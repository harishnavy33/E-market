package com.harishmart.model;

import java.math.BigDecimal;

public class Product {
    private long id;
    private long sellerId;
    private String name;
    private String description;
    private BigDecimal price;
    private int stock;

    public Product() {}

    public Product(long id, long sellerId, String name, String description, BigDecimal price, int stock) {
        this.id = id;
        this.sellerId = sellerId;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stock = stock;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getSellerId() { return sellerId; }
    public void setSellerId(long sellerId) { this.sellerId = sellerId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }

    public int getStock() { return stock; }
    public void setStock(int stock) { this.stock = stock; }
}