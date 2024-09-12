package com.arslankucukkafa.labormarketauth.idm.controller;

import com.arslankucukkafa.labormarketauth.mongo.LaborMarketDataInitializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class TestController {

    @GetMapping("/test")
    public String test() throws IOException {
//        laborMarketDataInitializer.initData();
        return "Test";
    }
}
