package com.arslankucukkafa.labormarketauth.idm.controller;

import com.arslankucukkafa.labormarketauth.action.Permissionable;
import com.arslankucukkafa.labormarketauth.idm.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.repository.RoleRepository;
import com.arslankucukkafa.labormarketauth.idm.repository.UserRepository;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@Permissionable
@RequestMapping("/api/v1/role")
public class RoleController {

    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public RoleController(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }


    @PostMapping
    public void createRole(@RequestBody String roleName) {
        RoleModel roleModel = new RoleModel();
        roleModel.setName(roleName);
        roleModel.setPermissions(Collections.emptyList());
        roleRepository.save(roleModel);
    }

 /*   // Yeni bir role oluşturur
    @PostMapping
    public void createRole() {
        // create role
    }

    // Parametre olarak verilen role'ü siler
    @DeleteMapping
    public void deleteRole() {
        // delete role
    }

    // Parametre olarak verilen role'ü günceller
    // arslan.kucukkafa : UpdateRole methodunda Actionlar ve Permissionlar ile ilgili güncelleme yapılabilir
    @PostMapping
    public void updateRole() {
        // update role
    }

    // Verilen id'ye sahip rolü getirir
    @PostMapping
    public void getRole() {
        // get role
    }

    // Tüm rolleri getirir
    @PostMapping
    public void getRoles() {
        // get roles
    }

    // arslan.kucukkafa: Aşşağıdaki 3 metotun işlevleri user ile ilişkili
    // User'a rol atar
    @PostMapping
    public void assignRole() {
        // assign role
    }

    // Mevcut rolü kaldırır
    @PostMapping
    public void unassignRole() {
        // unassign role
    }

    // Mevcut rolünü getirir
    @GetMapping
    public void getAssignedRole() {
        // get assigned roles
    }*/

}
