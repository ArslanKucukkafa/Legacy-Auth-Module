package com.arslankucukkafa.labormarketauth.idm.role.service;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.role.model.dto.RoleDto;
import com.arslankucukkafa.labormarketauth.idm.role.repository.RoleRepository;
import com.arslankucukkafa.labormarketauth.util.EndpointScanner;
import com.arslankucukkafa.labormarketauth.util.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private EndpointScanner endpointScanner;

    public RoleService(RoleRepository roleRepository, EndpointScanner endpointScanner) {
        this.roleRepository = roleRepository;
        this.endpointScanner = endpointScanner;
    }

    public List<Permission> getAllPermissions() {
        List<Permission> permissions = new ArrayList<>();
        try {
             endpointScanner.getEndpoints().forEach((key, value) -> permissions.add(new Permission(key, value)));
             return permissions;
        } catch (Exception e) {
            throw new ResourceNotFoundException("Error occured while fetching permissions.");
        }
    }

    public RoleModel addPermissionToRole(String roleName, Permission permission) {
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
        role.getPermissons().add(permission);
        return roleRepository.save(role);
    }

    public RoleModel removePermissionFromRole(String roleName, Permission permission) {
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
        role.getPermissons().remove(permission);
        return roleRepository.save(role);
    }
    // ROLES

    public List<RoleModel> findAllRoles(){
        return roleRepository.findAll();
    }

    public RoleModel findRoleByName(String name){
        return roleRepository.findByName(name).orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + name));
    }

    public void createRole(RoleDto roleDto){
        RoleModel roleModel = roleDto.convertToRoleModel(roleDto);
        roleRepository.save(roleModel);
    }

    // TODO: RoleName'ler uniqe olacak
    public void deleteRole(String roleName) {
        RoleModel role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
        roleRepository.delete(role);
    }

    /**
     * Bu method kullanılarak permissionlar için de düzenleme işlemi yapılabilir */
    public RoleModel roleUpdate(RoleModel roleModel) {
        var role = roleRepository.findByName(roleModel.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleModel.getName()));
        return roleRepository.save(role);
    }

}
