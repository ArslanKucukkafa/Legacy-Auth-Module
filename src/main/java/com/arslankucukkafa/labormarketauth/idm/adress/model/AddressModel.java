package com.arslankucukkafa.labormarketauth.idm.adress.model;

import jakarta.annotation.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * AddressModel daha çok tek taraflı bir model oldu. Çünkü adres modellinde speesifik bir property bulunmoyor.
 * Anlamsal olarak UserModel içerisinde bir adres olduğunu düşünebiliriz.
 * İleride resourceUrn gibi bir property eklenerek bu modelin daha anlamlı hale getirilebilir.
 */
@Document
public class AddressModel {
    @Id
    private String id;
    @Nullable
    private String street;
    @Nullable
    private String city;
    @Nullable
    private String state;
    @Nullable
    private String zipCode;
    @Nullable
    private String country;

    public AddressModel() {
    }

    public AddressModel(String street, String city, String state, String zipCode, String country) {
        this.street = street;
        this.city = city;
        this.state = state;
        this.zipCode = zipCode;
        this.country = country;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public String getCity() {
        return city;
    }

    public String getState() {
        return state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public String getCountry() {
        return country;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setState(String state) {
        this.state = state;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public void setCountry(String country) {
        this.country = country;
    }
}
