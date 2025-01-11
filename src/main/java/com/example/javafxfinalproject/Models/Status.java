package com.example.javafxfinalproject.Models;

public enum Status {
    SUCCESS("Success"),
    ERROR("Error");

    private final String status;

    Status(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
