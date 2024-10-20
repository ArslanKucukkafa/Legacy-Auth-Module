package com.arslankucukkafa.labormarketauth.util;

import com.arslankucukkafa.labormarketauth.idm.permission.model.PermissonModel;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
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

    private RedisTemplate<String, List<PermissonModel>> redisTemplate;

    private MongoTemplate mongoTemplate;
    @Value("${app.public.role}")
    private String publicRole;

    public PrincipalHolder(RedisTemplate<String, List<PermissonModel>> redisTemplate, MongoTemplate mongoTemplate) {
        this.redisTemplate = redisTemplate;
        this.mongoTemplate = mongoTemplate;
    }

    // arslan.kucukkafa: Birden fazla yerde kullanılabilecegi için kod tekrarını önlemek adına bu metodu oluşturdum.
    public UserModel getCurrentPrincipal(){
        UserModel userModel = (UserModel) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return userModel;
    }

    /* arslan.kucukkafa: Bu metot her zaman yetkisiz erişime açık olan endpointlerin izinlerini döner
        ,yazılmasının bir diger sebebi ise permitall da gelen userlar için erişim izinlerini dönmek.
        */
    public List<PermissonModel> getAlwaysAllowedPermissions(){
        // check redis for permissions
        List<PermissonModel> permissonModels = redisTemplate.opsForValue().get(publicRole);

        if(permissonModels.isEmpty()){
            // redis not found, get from db
            Query query = new Query();
            Criteria criteria = new Criteria();
            criteria.orOperator(
                    Criteria.where("name").is(publicRole)
            );
            query.addCriteria(criteria);
            RoleModel roleModel = mongoTemplate.findOne(query,RoleModel.class);
            if(roleModel == null)
                permissonModels = roleModel.getPermissons();
                redisTemplate.opsForValue().set(publicRole, permissonModels);
            }

        return permissonModels;
    }

}
