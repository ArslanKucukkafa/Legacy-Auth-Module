package com.arslankucukkafa.labormarketauth.idm.auth.model.sync;

public enum Provider {
    GITHUB("github"),
    GOOGLE("google"),
    LINKEDIN("linkedin"),
    DAO_PROVIDER("dao_provider");

    private final String providerName;

    Provider(String providerName) {
        this.providerName = providerName;
    }

    public String getProviderName() {
        return providerName;
    }

}