package com.example.javafxfinalproject.Models;

import java.util.Date;

public class Discount {

    private int id;
    private String code;
    private Date expiryDate;
    private double discounted_amount; // not in percent, range from 0 to 1;

    public Discount(int id, String code, Date expiryDate, double discounted_amount) {
        this.id = id;
        this.code = code;
        this.expiryDate = expiryDate;
        this.discounted_amount = discounted_amount;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public double getDiscounted_amount() {
        return discounted_amount;
    }

    public void setDiscounted_amount(double discounted_amount) {
        this.discounted_amount = discounted_amount;
    }
}
