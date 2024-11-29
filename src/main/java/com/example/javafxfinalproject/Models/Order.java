package com.example.javafxfinalproject.Models;

import java.util.ArrayList;
import java.util.List;

public class Order {
    private int id;
    private int userId;
    private User user;
    private int addressId;

    private Address address;
    private double totalAmount;
    private String status;
    private boolean isDiscounted = false;
    private Integer discountId = null;
    private Discount discount = null;
    private List<OrderItem> orderItems = null;

    public Order() {}


    public Order(int id, int userId, int addressId, double totalAmount, String status, int discountId, Discount discount) {
        this.id = id;
        this.userId = userId;
        this.addressId = addressId;
        this.totalAmount = totalAmount;
        this.status = status;
        this.discountId = discountId;
        this.discount = discount;
        this.orderItems = new ArrayList<>();
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

    public int getAddressId() {
        return addressId;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
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

    public Discount getDiscount() {
        return discount;
    }

    public void setDiscount(Discount discount) {
        this.discount = discount;
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public void setDiscountId(Integer discountId) {
        this.discountId = discountId;
    }
    public void addOrderItem(OrderItem item)
    {
        this.orderItems.add(item);
    }
    public List<OrderItem> getOrderItems() {
        return orderItems;
    }
}
