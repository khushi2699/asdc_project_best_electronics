package com.best.electronics.exceptions;
public class ErrorMessage {

    private  int StatusCode;

    private final String message;


    public ErrorMessage(int statusCode, String message) {
        StatusCode = statusCode;
        this.message = message;
    }

    public int getStatusCode() {
        return StatusCode;
    }

    public String getMessage() {
        return message;
    }
}