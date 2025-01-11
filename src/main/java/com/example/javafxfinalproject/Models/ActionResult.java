package com.example.javafxfinalproject.Models;



public class ActionResult<T> {
    private Status status;
    private T data;

    private String message;

    public ActionResult(Status status, T data, String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    public Status getStatus() {
        return status;
    }

    public T getData() {
        return data;
    }
    public String getMessage() {
        return message;
    }


    public static <T> ActionResult<T> success(T data , String message) {
        return new ActionResult<>(Status.SUCCESS, data , message );
    }

    public static <T> ActionResult<T> error(T data, String message) {
        return new ActionResult<>(Status.ERROR, data , message);
    }
}
