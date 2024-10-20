package com.arslankucukkafa.labormarketauth.idm.role.controller;

import com.arslankucukkafa.labormarketauth.idm.permission.model.PermissonModel;
import com.arslankucukkafa.labormarketauth.idm.role.service.RoleService;
import com.arslankucukkafa.labormarketauth.util.exception.ResourceNotFoundException;
import com.arslankucukkafa.labormarketauth.idm.role.model.dto.RoleDto;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// arslan.kucukkafa: burada repository kullanılmış, bunu diger controller classları gibi servis üzerinden yapmak daha iyi olurdu

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public void createRole(@RequestBody RoleDto roleDto) {
        roleService.createRole(roleDto);
    }

    @GetMapping("/getAll")
    public List<RoleModel> getAllRoles() {
        return roleService.findAllRoles();
    }

    @GetMapping("/get/{roleName}")
    public RoleModel getRole(@PathVariable("roleName") String roleName) {
        return roleService.findRoleByName(roleName);
    }

    @DeleteMapping("/delete/{roleName}")
    public void deleteRole(@PathVariable("roleName") String roleName) {
        roleService.deleteRole(roleName);
    }

    @GetMapping("/getPermissions")
    public List<PermissonModel> getPermissions() throws Exception {
            return roleService.findAllPermissions();
    }

    @PostMapping("/addPermission/")
    public ResponseEntity<String> addPermission(@RequestParam("roleName") String roleName, @RequestParam("permissionId") String permissionId) {
        try {
            roleService.addPermissionToRole(roleName, permissionId);
            return new ResponseEntity<>("permission add to role",HttpStatus.OK);
        } catch (ResourceNotFoundException rsc) {
            return new ResponseEntity<>(rsc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/deletePermission/{roleName}")
    public ResponseEntity<String> deletePermission(@RequestParam("roleName") String roleName, @RequestParam("permissionId") String permissionId) {
        try {
            roleService.deletePermissionFromRole(roleName, permissionId);
            return new ResponseEntity<>("permission add to role",HttpStatus.OK);
        } catch (ResourceNotFoundException rsc) {
            return new ResponseEntity<>(rsc.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping("/updatePermissions")
    public ResponseEntity<String> updatePermissions() {
        roleService.updatePermissions();
        return new ResponseEntity<>("Permissions updated", HttpStatus.OK);
    }

}
