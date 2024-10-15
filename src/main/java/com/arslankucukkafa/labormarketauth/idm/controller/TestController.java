package com.arslankucukkafa.labormarketauth.idm.controller;

import com.arslankucukkafa.labormarketauth.idm.permisson.EndpointScanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.HashMap;

@RestController
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
    public HashMap<RequestMethod, String> scan() throws IOException {
        try {
            return enpointScanner.scanEndpoints();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
