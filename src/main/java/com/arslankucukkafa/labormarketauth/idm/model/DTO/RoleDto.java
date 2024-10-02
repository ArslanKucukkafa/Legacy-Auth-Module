package com.arslankucukkafa.labormarketauth.idm.model.DTO;

import com.arslankucukkafa.labormarketauth.idm.model.RoleModel;

public class RoleDto {

    public String name;

    public RoleDto(String name) {
        this.name = name;
    }

    public RoleModel convertToRoleModel(RoleDto roleDto) {
        RoleModel roleModel = new RoleModel();
        roleModel.setName(roleDto.getName());
        return roleModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
