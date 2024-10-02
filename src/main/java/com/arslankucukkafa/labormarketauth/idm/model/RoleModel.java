package com.arslankucukkafa.labormarketauth.idm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
public class RoleModel {
    @Id
    private String id;
    private String name;
    private PermissonModel permisson;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PermissonModel getPermisson() {
        return permisson;
    }

    public void setPermisson(PermissonModel permisson) {
        this.permisson = permisson;
    }
}
