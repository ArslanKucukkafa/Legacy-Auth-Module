package com.arslankucukkafa.labormarketauth.idm.model.DTO;

import com.arslankucukkafa.labormarketauth.idm.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.model.UserModel;
import jakarta.validation.constraints.NotNull;
import org.springframework.boot.context.properties.bind.DefaultValue;

import java.util.ArrayList;

public class UserRegisterDto {
    @NotNull
    private String name;
    @NotNull
    private String surname;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String birthDate;
    @NotNull
    private int version;
    private ContactModel contact;
    private AddressModel address;


    public UserRegisterDto(String name, String surname, String username, String password, String birthDate,int version, ContactModel contact, AddressModel address) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.contact = contact;
        this.address = address;
        this.version = version;
    }

    public UserRegisterDto() {
    }

    public UserModel toUserModel() {
        UserModel userModel = new UserModel();
        userModel.setName(name);
        userModel.setSurname(surname);
        userModel.setUsername(username);
        userModel.setPassword(password);
        userModel.setBirthDate(birthDate);
        userModel.setVersion(version);
        userModel.setContact(contact);
        userModel.setRole(new ArrayList<>());
        userModel.setAccountVerified(false);
        userModel.setAddress(address);
        return userModel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public ContactModel getContact() {
        return contact;
    }

    public void setContact(ContactModel contact) {
        this.contact = contact;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
