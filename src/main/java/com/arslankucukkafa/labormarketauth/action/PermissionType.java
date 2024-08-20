package com.arslankucukkafa.labormarketauth.action;

public enum PermissionType {

    FOR_EVERYONE("FOR_EVERYONE"),
    FOR_SUPERUSER("FOR_SUPERUSER"),;
    private String value;

    private PermissionType(String value) {
        this.value = value;
    }
}
