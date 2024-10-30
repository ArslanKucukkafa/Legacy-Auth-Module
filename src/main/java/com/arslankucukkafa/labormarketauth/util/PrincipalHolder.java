package com.arslankucukkafa.labormarketauth.util;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.role.repository.RoleRepository;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import com.arslankucukkafa.labormarketauth.util.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class PrincipalHolder {

    private RedisTemplate<String, List<Permission>> redisTemplate;

    private RoleRepository roleRepository;
    @Value("${app.public.role}")
    private String publicRole;

    public PrincipalHolder(RedisTemplate<String, List<Permission>> redisTemplate, RoleRepository roleRepository) {
        this.redisTemplate = redisTemplate;
        this.roleRepository = roleRepository;
    }

    // arslan.kucukkafa: Birden fazla yerde kullanılabilecegi için kod tekrarını önlemek adına bu metodu oluşturdum.
    public List<Permission> getCurrentPrincipal(){
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<Permission> permissions = new ArrayList<>();
        List<String> roles = userModel.getRoles();
        for(String role: roles){
            RoleModel roleModel = roleRepository.findByName(role).orElse(null);
            if(roleModel != null) {
                permissions.addAll(roleModel.getPermissons());
            }
        }
        return permissions;
    }

    /* arslan.kucukkafa: Bu metot her zaman yetkisiz erişime açık olan endpointlerin izinlerini döner
        ,yazılmasının bir diger sebebi ise permitall da gelen userlar için erişim izinlerini dönmek.
        */
    public List<Permission> getAlwaysAllowedPermissions(){
        // check redis for permissions
        List<Permission> grantedPublicPermissions = redisTemplate.opsForValue().get(publicRole);
        if(grantedPublicPermissions == null){
            // redis not found, get from db
            RoleModel roleModel = roleRepository.findByName(publicRole).orElseThrow(() -> new ResourceNotFoundException("Role not found with name: " + publicRole));
            if(roleModel != null)
                grantedPublicPermissions = new ArrayList<>();
                grantedPublicPermissions.addAll(roleModel.getPermissons());
                redisTemplate.opsForValue().set(publicRole, grantedPublicPermissions);
            }
        return grantedPublicPermissions;
    }

}
