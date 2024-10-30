package com.arslankucukkafa.labormarketauth.idm.role.model.dto;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import jakarta.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class RoleDto {

    public String name;
    public String description;
    @Nullable
    public List<Permission> permissions;

    public RoleDto(String name, String description, List<Permission> permissions) {
        this.name = name;
        this.description = description;
        this.permissions = permissions;
    }

    public RoleModel convertToRoleModel(RoleDto roleDto) {
        RoleModel roleModel = new RoleModel();
        roleModel.setName(roleDto.getName());
        roleModel.setDescription(roleDto.getDescription());
        if (roleDto.getPermissions() == null){
            roleModel.setPermissons(new ArrayList<>());
        }
        roleModel.setPermissons(roleDto.getPermissions());
        return roleModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
