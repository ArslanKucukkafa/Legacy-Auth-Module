package com.arslankucukkafa.labormarketauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class})
public class LaborMarketAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(LaborMarketAuthApplication.class, args);
    }

}
