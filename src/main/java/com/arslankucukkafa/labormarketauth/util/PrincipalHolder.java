package com.arslankucukkafa.labormarketauth.util;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import com.arslankucukkafa.labormarketauth.idm.role.repository.RoleRepository;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrincipalHolder {

    private RedisTemplate<String, RoleModel> redisTemplate;

    private RoleRepository roleRepository;
    @Value("${app.public.role}")
    private String publicRole;

    public PrincipalHolder(RedisTemplate<String, RoleModel> redisTemplate, RoleRepository roleRepository) {
        this.redisTemplate = redisTemplate;
        this.roleRepository = roleRepository;
    }

    // arslan.kucukkafa: Birden fazla yerde kullanılabilecegi için kod tekrarını önlemek adına bu metodu oluşturdum.
    public UserModel getCurrentPrincipal(){
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userModel;
    }

    /* arslan.kucukkafa: Bu metot her zaman yetkisiz erişime açık olan endpointlerin izinlerini döner
        ,yazılmasının bir diger sebebi ise permitall da gelen userlar için erişim izinlerini dönmek.
        */
    public List<Permission> getAlwaysAllowedPermissions(){
        // check redis for permissions
        RoleModel allowedRole = redisTemplate.opsForValue().get(publicRole);

        if(allowedRole == null){
            // redis not found, get from db
            RoleModel roleModel = roleRepository.findByName(publicRole).orElse(null);
            if(roleModel != null)
                redisTemplate.opsForValue().set(publicRole, roleModel);
            }
        return allowedRole.getPermissons();
    }

}
