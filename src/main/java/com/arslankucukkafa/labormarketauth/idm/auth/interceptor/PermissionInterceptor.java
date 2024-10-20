package com.arslankucukkafa.labormarketauth.idm.auth.interceptor;

import com.arslankucukkafa.labormarketauth.idm.permission.model.PermissonModel;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import com.arslankucukkafa.labormarketauth.util.PrincipalHolder;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.List;

//
@Component
public class PermissionInterceptor extends PrincipalHolder implements HandlerInterceptor {

    public PermissionInterceptor(RedisTemplate<String, List<PermissonModel>> redisTemplate, MongoTemplate mongoTemplate) {
        super(redisTemplate, mongoTemplate);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        var currentPrinciapl = getCurrentPrincipal();
        // arslan.kucukkafa: Bu metot her zaman yetkisiz erişime açık olan endpointlere giris izni verir.
        for (PermissonModel permission: getAlwaysAllowedPermissions()){
            if (request.getRequestURI().contains(permission.getPath()) && request.getMethod().equals(permission.getHttpMethod())){
                return true;
            }
        }
        // arslan.kucukkafa: Bu metot sahip olunan yetkilere göre endpointlere giris izni verir.
        if(currentPrinciapl != null){
            for (RoleModel role: currentPrinciapl.getRoles()){
                for (PermissonModel permission: role.getPermissons()){
                    if (request.getRequestURI().contains(permission.getPath()) && request.getMethod().equals(permission.getHttpMethod())){
                        return true;
                    }
                }
            }
        }

        return false;
    }
    

}
