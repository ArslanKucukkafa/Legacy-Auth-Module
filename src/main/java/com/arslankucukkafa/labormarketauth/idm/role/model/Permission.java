package com.arslankucukkafa.labormarketauth.idm.role.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;

public class Permission implements Serializable {
    private String path;
    private String requestMethod;

    @JsonCreator
    public Permission(@JsonProperty("path") String path, @JsonProperty("requestMethod") String requestMethod) {
        this.path = path;
        this.requestMethod = requestMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(String requestMethod) {
        this.requestMethod = requestMethod;
    }
}
