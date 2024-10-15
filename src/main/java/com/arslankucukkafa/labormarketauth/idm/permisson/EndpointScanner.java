package com.arslankucukkafa.labormarketauth.idm.permisson;

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
import java.util.Map;

@Component
public class EndpointScanner implements ApplicationListener<ContextRefreshedEvent>{

    private final Logger LOGGER = LoggerFactory.getLogger(EndpointScanner.class);

    @Autowired
    private ApplicationContext applicationContext;



    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        try {
            scanEndpoints();
        } catch (Exception e) {
            System.out.println("Error while scanning endpoints");
        }
    }

    public HashMap<RequestMethod, String> scanEndpoints () throws Exception {
        RequestMappingHandlerMapping requestMappingHandlerMapping = applicationContext
                .getBean("requestMappingHandlerMapping", RequestMappingHandlerMapping.class);
        Map<RequestMappingInfo, HandlerMethod> map = requestMappingHandlerMapping
                .getHandlerMethods();
        HashMap<RequestMethod, String> endpoints = new HashMap<>();
        map.forEach((key, value) -> endpoints.put(key.getMethodsCondition().getMethods().iterator().next(), key.getPathPatternsCondition().getDirectPaths().iterator().next()) );
        return endpoints;
    }
}