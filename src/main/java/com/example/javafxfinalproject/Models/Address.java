package com.example.javafxfinalproject.Models;

public class Address {
    private int id;
    private int userId;
    private String country;
    private String city;
    private String street;
    private String building;
    private String postalCode;

    public Address() {}

    public Address(int id, int userId, String country, String city, String street, String building, String postalCode) {
        this.id = id;
        this.userId = userId;
        this.country = country;
        this.city = city;
        this.street = street;
        this.building = building;
        this.postalCode = postalCode;
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }
}
