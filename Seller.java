package com.harishmart.model;

public class Seller {
    private long id;
    private long userId;
    private String shopName;

    public Seller() {}

    public Seller(long id, long userId, String shopName) {
        this.id = id;
        this.userId = userId;
        this.shopName = shopName;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public long getUserId() { return userId; }
    public void setUserId(long userId) { this.userId = userId; }

    public String getShopName() { return shopName; }
    public void setShopName(String shopName) { this.shopName = shopName; }
}