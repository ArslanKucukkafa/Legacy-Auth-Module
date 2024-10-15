package com.arslankucukkafa.labormarketauth.idm.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class RoleModel {
    @Id
    private String id;
    private String name;
    private List<PermissonModel> permissons;
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

    public List<PermissonModel> getPermissons() {
        return permissons;
    }

    public void setPermissons(List<PermissonModel> permissons) {
        this.permissons = permissons;
    }

    public void addPermissons(PermissonModel permissons) {
        this.permissons.add(permissons);
    }
}
