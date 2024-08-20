package com.arslankucukkafa.labormarketauth.idm.model;

import com.arslankucukkafa.labormarketauth.action.Permission;
import com.arslankucukkafa.labormarketauth.action.Permissionable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Permissionable
public class RoleModel {
    @Id
    private String id;
    private String name;
    private List<Permission> permissions;

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


}
