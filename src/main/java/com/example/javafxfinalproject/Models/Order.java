package com.example.javafxfinalproject.Models;

public class Order {
    private int id;
    private int userId;
    private String address;
    private double totalAmount;
    private String status;
    private boolean isDiscounted = false;
    private Integer discountId = null;

    public Order() {}

    public Order(int id, int userId, String address, double totalAmount, String status) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
    }

    public Order(int id, int userId, String address, double totalAmount, String status, boolean isDiscounted, int discountId) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
        this.isDiscounted = isDiscounted;
        this.discountId = discountId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public double getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(double totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public boolean isDiscounted() {
        return isDiscounted;
    }

    public void setDiscounted(boolean discounted) {
        isDiscounted = discounted;
    }

    public int getDiscountId() {
        return discountId;
    }

    public void setDiscountId(int discountId) {
        this.discountId = discountId;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }

}
