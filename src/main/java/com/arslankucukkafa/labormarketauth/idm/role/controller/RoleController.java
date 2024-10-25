package com.arslankucukkafa.labormarketauth.idm.role.controller;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.role.model.dto.RoleDto;
import com.arslankucukkafa.labormarketauth.idm.role.service.RoleService;
import com.arslankucukkafa.labormarketauth.util.exception.ResourceNotFoundException;
import com.arslankucukkafa.labormarketauth.util.response.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// arslan.kucukkafa: burada repository kullanılmış, bunu diger controller classları gibi servis üzerinden yapmak daha iyi olurdu

@RestController
@RequestMapping("/api/v1/roles")
public class RoleController {

    private RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping
    public void createRole(@RequestBody RoleDto roleDto) {
        roleService.createRole(roleDto);
    }

    @GetMapping()
    public List<RoleModel> getAllRoles() {
        return roleService.findAllRoles();
    }

    @GetMapping("/{roleName}")
    public ResponseEntity<ApiResponse<RoleModel>> getRole(@PathVariable("roleName") String roleName) {
        try {
            var role = roleService.findRoleByName(roleName);
            return ResponseEntity.ok(new ApiResponse<>("Role found", role, HttpStatus.OK));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.ok(new ApiResponse<>("Role not found", null, HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            return ResponseEntity.ok(new ApiResponse<>("Exception occured", null, HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/{roleName}")
    public ResponseEntity<ApiResponse<String>> deleteRole(@PathVariable("roleName") String roleName) {
        try {
            roleService.deleteRole(roleName);
            return ResponseEntity.ok(new ApiResponse<>("Role deleted", roleName, HttpStatus.NO_CONTENT));
        } catch (ResourceNotFoundException ex) {
            return ResponseEntity.ok(new ApiResponse<>("Role not found", roleName, HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            return ResponseEntity.ok(new ApiResponse<>("Exception occured", ex.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }


    @GetMapping("/permissions")
    public ResponseEntity<ApiResponse<List<Permission>>> getPermissions() throws Exception {
        try {
            List<Permission> permissions = roleService.getAllPermissions();
            return ResponseEntity.ok(new ApiResponse<>("Current Endpoints Informations", permissions, HttpStatus.OK));
        } catch (Exception ex) {
            return ResponseEntity.ok(new ApiResponse<>(ex.getMessage(), null, HttpStatus.BAD_REQUEST));
        }
    }

    @PostMapping("/{roleName}/permissions")
    public ResponseEntity<ApiResponse<String>> addPermission(@RequestParam("roleName") String roleName, @RequestBody Permission permission) {
        try {
            var model =  roleService.addPermissionToRole(roleName, permission);
            StringBuilder message = new StringBuilder("permission added to role: ").append(roleName);
            return ResponseEntity.ok(new ApiResponse<>(message.toString(), model.toString() ,HttpStatus.OK));
        } catch (ResourceNotFoundException ex) {
            StringBuilder message = new StringBuilder("role not found with name: ").append(roleName);
            return ResponseEntity.ok(new ApiResponse<>(message.toString(), ex.getMessage(),HttpStatus.NOT_FOUND));
        } catch (Exception ex) {
            StringBuilder message = new StringBuilder("exception occure when to add: ").append(permission.toString()).append(" to role: ").append(roleName);
            return ResponseEntity.ok(new ApiResponse<>(message.toString(), ex.getMessage(),HttpStatus.BAD_REQUEST));
        }
    }

    @DeleteMapping("/{roleName}/permissions")
    public ResponseEntity<ApiResponse<String>> deleteRole(@RequestParam("roleName") String roleName, @RequestBody Permission permission) {
        try {
            var model = roleService.removePermissionFromRole(roleName, permission);
            StringBuilder message = new StringBuilder("permission removed from role: ").append(roleName);
            return ResponseEntity.ok(new ApiResponse<>(message.toString(), model.toString(), HttpStatus.NO_CONTENT));
        } catch (ResourceNotFoundException ex) {
            StringBuilder message = new StringBuilder("exception occure when to remove: ").append(permission.toString()).append(" from role: ").append(roleName);
            return ResponseEntity.ok(new ApiResponse<>(message.toString(), ex.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }


    @PutMapping("/{roleName}")
    public ResponseEntity<ApiResponse<String>> updateRole(@RequestBody RoleModel roleModel) {
        try {
            var model = roleService.roleUpdate(roleModel);
            StringBuilder message = new StringBuilder("Permissions updated for role: ").append(roleModel.getName());
            return ResponseEntity.ok(new ApiResponse<>(message.toString(), model.toString(), HttpStatus.OK));
        } catch (ResourceNotFoundException ex) {
            StringBuilder message = new StringBuilder("exception occure when to update role: ").append(roleModel.getName());
            return ResponseEntity.ok(new ApiResponse<>(message.toString(), ex.getMessage(), HttpStatus.BAD_REQUEST));
        }
    }

}
