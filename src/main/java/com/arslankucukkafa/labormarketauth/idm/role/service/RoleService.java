package com.arslankucukkafa.labormarketauth.idm.role.service;

import com.arslankucukkafa.labormarketauth.idm.permission.model.PermissonModel;
import com.arslankucukkafa.labormarketauth.idm.permission.repository.PermissionRepository;
import com.arslankucukkafa.labormarketauth.util.exception.ResourceNotFoundException;
import com.arslankucukkafa.labormarketauth.idm.role.model.dto.RoleDto;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.role.repository.RoleRepository;
import com.arslankucukkafa.labormarketauth.util.EndpointScanner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RoleService {

    private RoleRepository roleRepository;
    private PermissionRepository permissionRepository;
    private EndpointScanner endpointScanner;

    public RoleService(RoleRepository roleRepository, PermissionRepository permissionRepository, EndpointScanner endpointScanner) {
        this.roleRepository = roleRepository;
        this.permissionRepository = permissionRepository;
        this.endpointScanner = endpointScanner;
    }

    // PERMİSSİONS
    public List<PermissonModel> findAllPermissions(){
        return permissionRepository.findAll();
    }

    @Transactional
    public void addPermissionToRole(String roleName, String permissionPath) {
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));
        var permission = permissionRepository.findById(permissionPath)
                .orElseThrow(() -> new ResourceNotFoundException("Permission not found with id: " + permissionPath));
        role.addPermissons(permission);
        roleRepository.save(role);
    }

    public void deletePermissionFromRole(String roleName, String permissionPath) {
        var role = roleRepository.findByName(roleName)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + roleName));

        // arslan.kucukkafa böyle bir permission var mı diye kontrol etmiyoruz. Çünkü biz zaten var olanları gösteriyoruz. onu da kontrol etmeyelim aq
        role.getPermissons().removeIf(permission -> permission.getPath().equals(permissionPath));

        roleRepository.save(role);
    }

    // ROLES

    public List<RoleModel> findAllRoles(){
        return roleRepository.findAll();
    }

    public RoleModel findRoleByName(String name){
        return roleRepository.findByName(name).orElseThrow(NullPointerException::new);
    }

    public void createRole(RoleDto roleDto){
        RoleModel roleModel = new RoleModel();
        roleModel.setDescription(roleDto.getDescription());
        roleModel.setName(roleDto.getName());
        roleModel.setPermissons(roleDto.getPermissions());
        roleRepository.save(roleModel);
    }

    // TODO: RoleName'ler uniqe olacak
    public void deleteRole(String roleName){
        roleRepository.deleteRoleModelByName(roleName);
    }

    // arslan.kucukkafa admin update etmeyi gerekli gördügünde kullanılablir
    public void updatePermissions(){
        permissionRepository.deleteAll();
        try {
            List<PermissonModel> permissonModelList = endpointScanner.scanEndpoints();
            permissionRepository.saveAll(permissonModelList);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
