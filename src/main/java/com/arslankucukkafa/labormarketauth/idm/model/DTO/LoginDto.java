package com.arslankucukkafa.labormarketauth.idm.model.DTO;

public record LoginDto(String username, String password) {
    public LoginDto {
        if (username == null || username.isBlank() || username.length()<7) {
            throw new IllegalArgumentException("Username cannot be null or empty");
        }
        if (password == null || password.isBlank() || password.length()<7) {
            throw new IllegalArgumentException("Password cannot be null or empty");
        }
    }
}

