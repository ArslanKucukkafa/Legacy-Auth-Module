package com.arslankucukkafa.labormarketauth.mongo;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import jakarta.annotation.PostConstruct;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class LaborMarketDataInitializer {
    private final MongoTemplate mongoTemplate;
    private final ObjectMapper objectMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(LaborMarketDataInitializer.class);

    public LaborMarketDataInitializer(MongoTemplate mongoTemplate, ObjectMapper objectMapper) {
        this.mongoTemplate = mongoTemplate;
        this.objectMapper = objectMapper;
    }

    @PostConstruct
    public void init() throws IOException {

        mongoTemplate.dropCollection("roleModel");


        var loader = new PathMatchingResourcePatternResolver();
        Resource [] resource = loader.getResources("classpath*:*.init.json");

        for (Resource res : resource) {
            var directoryName = StringUtils.split(res.getFilename(), ".");
            String collectionName = Objects.requireNonNull(directoryName)[0];
            Document[] docs = null;
            try {
                docs = objectMapper.readValue(res.getInputStream(), Document[].class);
            } catch (MismatchedInputException ex) {
                LOGGER.error("Error while reading the file: " + res.getFilename() +"  not contains [] bracket or maybe is null ", ex.getMessage());
            }
            if (docs == null) {
                LOGGER.warn("No data found in the file: " + res.getFilename());
            } else {
                List<Document> documentList = List.of(docs);
                mongoTemplate.insert(documentList, collectionName);
            }
        }

    }

}
