package com.arslankucukkafa.labormarketauth.idm.model.DTO;

import com.arslankucukkafa.labormarketauth.action.Permission;
import com.arslankucukkafa.labormarketauth.idm.model.RoleModel;

import java.util.List;

public class RoleDto {

    public String name;
    public List<Permission> permissions;

    public RoleDto(String name, List<Permission> permissions) {
        this.name = name;
        this.permissions = permissions;
    }

    public RoleModel convertToRoleModel(RoleDto roleDto) {
        RoleModel roleModel = new RoleModel();
        roleModel.setName(roleDto.getName());
        roleModel.setPermissions(roleDto.getPermissions());
        return roleModel;
    }

    public String getName() {
        return name;
    }

    public List<Permission> getPermissions() {
        return permissions;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions = permissions;
    }
}
