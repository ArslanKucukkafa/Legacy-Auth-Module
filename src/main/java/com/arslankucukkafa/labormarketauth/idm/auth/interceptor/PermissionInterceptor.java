package com.arslankucukkafa.labormarketauth.idm.auth.interceptor;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import com.arslankucukkafa.labormarketauth.idm.role.repository.RoleRepository;
import com.arslankucukkafa.labormarketauth.util.PrincipalHolder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.regex.Pattern;

//
@Component
public class PermissionInterceptor extends PrincipalHolder implements HandlerInterceptor {


    public PermissionInterceptor(RedisTemplate<String, List<Permission>> redisTemplate, RoleRepository roleRepository) {
        super(redisTemplate, roleRepository);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        // arslan.kucukkafa: Bu metot her zaman yetkisiz erişime açık olan endpointlere giris izni verir.
        boolean allowAll = requestUriMatcher(request, getAlwaysAllowedPermissions());
        if (allowAll) return allowAll;

        // arslan.kucukkafa: Bu metot sahip olunan izinlere göre endpointe erişim izni verir.
        boolean personalAllow = requestUriMatcher(request, getCurrentPrincipal());
        if (personalAllow) return personalAllow;


        return false;
    }
    private boolean requestUriMatcher(HttpServletRequest request, List<Permission> grantedPermissions) {


        ObjectMapper mapper = new ObjectMapper();
        List<Permission> processedPermissions = mapper.convertValue(grantedPermissions, new TypeReference<List<Permission>>() {});

        String decodedRequestUri = URLDecoder.decode(request.getRequestURI(), StandardCharsets.UTF_8);

        for (Permission permission: processedPermissions){
            String regexPath = permission.getPath().replaceAll("\\{[^/]+\\}", "[^/]+");
            Pattern pattern = Pattern.compile(regexPath);
            if (pattern.matcher(decodedRequestUri).matches() && permission.getRequestMethod().matches(request.getMethod())){
                return true;
            }
        }

        return false;
    }


    

}
