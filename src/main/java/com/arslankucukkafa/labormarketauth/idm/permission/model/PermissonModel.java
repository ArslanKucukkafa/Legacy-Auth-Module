package com.arslankucukkafa.labormarketauth.idm.permission.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.RequestMethod;


/*
arslan.kucukkafa normalde db'de tutmaya gerek yok ama kullanıcı permissionları ekleyip
 çıkartırken yanlış bir context girmesi durumunda interceptor
   niye çalışmıyor diye düşünmesi yerine id ile permission işlemlerini devam ettirmesi
*/
@Document
public class PermissonModel {

    @Id
    private String path;
    private RequestMethod  requestMethod;

    public PermissonModel(RequestMethod requestMethod, String path) {
        this.requestMethod = requestMethod;
        this.path = path;
    }

    public RequestMethod getHttpMethod() {
        return requestMethod;
    }

    public void setHttpMethod(HttpMethod httpMethod) {
        this.requestMethod = requestMethod;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
