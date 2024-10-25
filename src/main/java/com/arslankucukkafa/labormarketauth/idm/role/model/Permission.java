package com.arslankucukkafa.labormarketauth.idm.role.model;

import org.springframework.web.bind.annotation.RequestMethod;

public class Permission {
    private String path;
    private RequestMethod requestMethod;

    public Permission(String path, RequestMethod requestMethod) {
        this.path = path;
        this.requestMethod = requestMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public RequestMethod getRequestMethod() {
        return requestMethod;
    }

    public void setRequestMethod(RequestMethod requestMethod) {
        this.requestMethod = requestMethod;
    }
}
