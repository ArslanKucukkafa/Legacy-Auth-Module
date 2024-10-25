package com.arslankucukkafa.labormarketauth.util.response;

import org.springframework.http.HttpStatus;

public class ApiResponse<T> {
    private String message;
    private T data;
    private HttpStatus status;

    public ApiResponse(String message, T data, HttpStatus status) {
        this.message = message;
        this.data = data;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public void setStatus(HttpStatus status) {
        this.status = status;
    }
}
