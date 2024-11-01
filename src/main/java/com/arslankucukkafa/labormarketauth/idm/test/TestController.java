package com.arslankucukkafa.labormarketauth.idm.test;

import com.arslankucukkafa.labormarketauth.idm.role.model.Permission;
import com.arslankucukkafa.labormarketauth.util.EndpointScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/test")
public class TestController  {

    @Autowired
    EndpointScanner enpointScanner;

    @GetMapping("/")
    public String index() {
        return "Hello World!";
    }

    @GetMapping("/test")
    public String test() throws IOException {
//        laborMarketDataInitializer.initData();
        return "Test";
    }

    @GetMapping("/scan")
    public List<Permission> scan() throws IOException {
        try {
            return enpointScanner.getEndpoints();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


/*    @GetMapping("/redis")
    public String testRedis() {
        redisTemplate.opsForValue().set("arslan", 1);
        return "Hello World!";
    }*/
}
