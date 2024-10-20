package com.arslankucukkafa.labormarketauth.idm.role.model.dto;

import com.arslankucukkafa.labormarketauth.idm.permission.model.PermissonModel;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import jakarta.annotation.Nullable;

import java.util.List;

public class RoleDto {

    public String name;
    public String description;
    @Nullable
    public List<PermissonModel> permissions;

    public RoleDto(String name) {
        this.name = name;
    }

    public RoleModel convertToRoleModel(RoleDto roleDto) {
        RoleModel roleModel = new RoleModel();
        roleModel.setName(roleDto.getName());
        roleModel.setDescription(roleDto.getDescription());
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

    public List<PermissonModel> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissonModel> permissions) {
        this.permissions = permissions;
    }
}
