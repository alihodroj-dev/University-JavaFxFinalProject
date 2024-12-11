package com.example.javafxfinalproject.Models;

public class Order {
    private int id;
    private int userId;
    private String address;
    private double totalAmount;
    private String status;

    public Order() {}

    public Order(int id, int userId, String address, double totalAmount, String status) {
        this.id = id;
        this.userId = userId;
        this.address = address;
        this.totalAmount = totalAmount;
        this.status = status;
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

}
