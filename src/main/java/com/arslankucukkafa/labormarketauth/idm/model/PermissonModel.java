package com.arslankucukkafa.labormarketauth.idm.model;

import org.springframework.http.HttpMethod;

public class PermissonModel {
    private HttpMethod  httpMethod;
    private String path;

    public PermissonModel(HttpMethod httpMethod, String path) {
        this.httpMethod = httpMethod;
        this.path = path;
    }

    public HttpMethod getHttpMethod() {
        return httpMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.httpMethod = httpMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
