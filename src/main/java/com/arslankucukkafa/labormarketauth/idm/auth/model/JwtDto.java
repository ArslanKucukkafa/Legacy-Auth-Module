package com.arslankucukkafa.labormarketauth.idm.auth.model;

public record JwtDto(String token) {
    public String getToken() {
        return "Bearer " + token;
    }
}