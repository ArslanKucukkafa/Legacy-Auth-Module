package com.arslankucukkafa.labormarketauth.idm.controller;

import com.arslankucukkafa.labormarketauth.idm.model.PermissonModel;
import com.arslankucukkafa.labormarketauth.idm.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.permisson.EndpointScanner;
import com.arslankucukkafa.labormarketauth.idm.repository.RoleRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

// arslan.kucukkafa: burada repository kullanılmış, bunu diger controller classları gibi servis üzerinden yapmak daha iyi olurdu

@RestController
@RequestMapping("/api/v1/role")
public class RoleController {

    private EndpointScanner endpointScanner;
    private final RoleRepository roleRepository;

    public RoleController(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }


    @PostMapping("/create/{roleName}")
    public void createRole(@PathVariable("roleName") String roleName) {
        RoleModel roleModel = new RoleModel();
        roleModel.setName(roleName);
        roleRepository.save(roleModel);
    }

    @GetMapping("/getAll}")
    public List<RoleModel> getAllRoles() {
        return roleRepository.findAll();
    }

    @GetMapping("/get/{roleName}")
    public RoleModel getRole(@PathVariable("roleName") String roleName) {
        return roleRepository.findByName(roleName).orElse(null);
    }

    @DeleteMapping("/delete/{roleName}")
    public void deleteRole(@PathVariable("roleName") String roleName) {
        RoleModel roleModel = roleRepository.findByName(roleName).orElse(null);
        if (roleModel != null) {
            roleRepository.delete(roleModel);
        }
    }

    @GetMapping("/getPermissions}")
    public HashMap<RequestMethod, String> getPermissions() throws Exception {
            return endpointScanner.scanEndpoints();
    }

    @PostMapping("/addPermission/{roleName}")
    public ResponseEntity<String> addPermission(@PathVariable("roleName") String roleName, @RequestBody PermissonModel permissonModel) {
        RoleModel roleModel = roleRepository.findByName(roleName).orElse(null);
        roleModel.getPermissons().add(permissonModel);
        return ResponseEntity.ok("Permission added");
    }

    @DeleteMapping("/deletePermission/{roleName}")
    public ResponseEntity<String> deletePermission(@PathVariable("roleName") String roleName, @RequestBody PermissonModel permissonModel) {
        RoleModel roleModel = roleRepository.findByName(roleName).orElse(null);
        roleModel.getPermissons().remove(permissonModel);
        return ResponseEntity.ok("Permission removed");
    }

    // arslan.kucukkafa: burası biraz saçma olmuş
    @PutMapping("/updatePermission}")
    public ResponseEntity<String> updatePermission( @RequestBody RoleModel roleModel) {
        roleRepository.save(roleModel);
        return ResponseEntity.ok("Permission updated");
    }
}
