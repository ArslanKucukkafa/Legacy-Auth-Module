package com.arslankucukkafa.labormarketauth.util.email;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;

@Component
public class EmailTemplateService {

    private final ResourceLoader resourceLoader;

    public EmailTemplateService(
            //@Qualifier("gridFsTemplate")
            ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    public String loadTemplate(String fileName) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/" + fileName);
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(resource.getInputStream(), StandardCharsets.UTF_8));
            return reader.lines().collect(Collectors.joining("\n"));
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String prepareAuthenticateEmailContent(String fileName, String code) {
        // Dinamik veri eklemek için string manipülasyonu
        return loadTemplate(fileName).replace("{{authenticate-code}}", code);
    }

    public String prepareGeneralEmailContent(String fileName, String title, String content) {
        // Dinamik veri eklemek için string manipülasyonu
        return loadTemplate(fileName)
                .replace("{{title}}", title)
                .replace("{{content}}", content);
    }



}
