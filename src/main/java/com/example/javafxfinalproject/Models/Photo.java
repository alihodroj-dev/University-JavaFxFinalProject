package com.example.javafxfinalproject.Models;

public class Photo {
    private int id;
    private int productId;
    private String url;
    private boolean isMain;

    public Photo() {}

    public Photo(int id, int productId, String url, boolean isMain) {
        this.id = id;
        this.productId = productId;
        this.url = url;
        this.isMain = isMain;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }
}
