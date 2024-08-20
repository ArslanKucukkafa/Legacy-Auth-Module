package com.arslankucukkafa.labormarketauth.idm.exception;

public class ErrorResponse {
    private int statusCode;
    private String message;

    public ErrorResponse(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
    // Getter methods
}
