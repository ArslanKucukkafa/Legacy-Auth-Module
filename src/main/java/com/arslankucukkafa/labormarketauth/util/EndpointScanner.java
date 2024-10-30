package com.arslankucukkafa.labormarketauth.util;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class EndpointScanner implements ApplicationListener<ContextRefreshedEvent>{

    private final Logger LOGGER = LoggerFactory.getLogger(EndpointScanner.class);

    @Autowired
    private ApplicationContext applicationContext;


    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            getEndpoints();
        } catch (Exception e) {
            System.out.println("Error while scanning endpoints");
        }
    }
    /**
     * FIXME Burda PermissionModel kullanılmış. dizin dahilinde burda kullanılmamalı. Çünkü burası bir util class ve bu class içerisinde model classları kullanılmamalı bence.
     *
     * @return PermissonModel listesi dönüyor. Refactor edilmeli.
     */


    public List<Permission> getEndpoints () throws Exception {
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
                .getHandlerMethods();
        List<Permission> permissionList = new ArrayList<>();
        map.forEach((key, value) -> {
            // Eğer method ismi "errorHtml" ise put işlemini atla
            if (!"basicErrorController".equals(value.getBean())) {
                var permission = new Permission(
                        key.getPatternValues().iterator().next(),
                        key.getMethodsCondition().getMethods().iterator().next().name());
                permissionList.add(permission);
            }
        });
        return permissionList;
    }

}