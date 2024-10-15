package com.arslankucukkafa.labormarketauth.idm.model.DTO;

public record JwtDto(String token) {
    public String getToken() {
        return "Bearer " + token;
    }
}