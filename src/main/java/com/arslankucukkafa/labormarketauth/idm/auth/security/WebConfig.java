package com.arslankucukkafa.labormarketauth.idm.auth.security;

import com.mongodb.ConnectionString;

import com.mongodb.client.MongoClient;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class WebConfig {
    @Bean
    public MongoClient mongo() {
        ConnectionString connectionString = new ConnectionString("mongodb://localhost:27017/test");
        //arslan.kucukkafa: MONGOATLAS_CONNECTİON_STRİNG -> "mongodb+srv://arslankucukkafa:mongoCloudPassword1@mongodb.fci20.mongodb.net/?retryWrites=true&w=majority&appName=mongoDB"
        MongoClientSettings mongoClientSettings = MongoClientSettings.builder()
                .applyConnectionString(connectionString)
                .build();

        return MongoClients.create(mongoClientSettings);
    }


    @Bean
    public MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "test");
    }
}
