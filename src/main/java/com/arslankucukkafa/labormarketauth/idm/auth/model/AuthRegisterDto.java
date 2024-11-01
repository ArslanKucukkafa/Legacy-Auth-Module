package com.arslankucukkafa.labormarketauth.idm.auth.model;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.auth.model.sync.Provider;
import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import jakarta.validation.constraints.NotNull;

import java.util.ArrayList;

public class AuthRegisterDto {
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


    public AuthRegisterDto(String name, String surname, String username, String password, String birthDate, int version, ContactModel contact, AddressModel address) {
        this.name = name;
        this.surname = surname;
        this.username = username;
        this.password = password;
        this.birthDate = birthDate;
        this.contact = contact;
        this.address = address;
        this.version = version;
    }

    public AuthRegisterDto() {
    }

    public UserModel toUserModel(AuthRegisterDto authRegisterDto) {
        UserModel userModel = new UserModel();
        userModel.setName(authRegisterDto.getName());
        userModel.setSurname(authRegisterDto.getSurname());
        userModel.setUsername(authRegisterDto.getUsername());
        userModel.setPassword(authRegisterDto.getPassword());
        userModel.setBirthDate(authRegisterDto.getBirthDate());
        userModel.setVersion(authRegisterDto.getVersion());
        userModel.setRole(new ArrayList<>());
        userModel.setProvider(new ArrayList<>());
        userModel.getProviders().add(Provider.DAO_PROVIDER);
        userModel.setAccountVerified(false);
        return userModel;
    }

    public ContactModel toContactModel(AuthRegisterDto authRegisterDto) {
        ContactModel contactModel = new ContactModel();
        contactModel.setAvatar_url(authRegisterDto.getContact().getAvatar_url());
        contactModel.setEmail(authRegisterDto.getContact().getEmail());
        contactModel.setGithub_url(authRegisterDto.getContact().getGithub_url());
        contactModel.setPhone(authRegisterDto.getContact().getPhone());
        contactModel.setAdditionalInfo(authRegisterDto.getContact().getAdditionalInfo());
        return contactModel;
    }

    public AddressModel toAddressModel(AuthRegisterDto authRegisterDto) {
        AddressModel addressModel = new AddressModel();
        addressModel.setCity(authRegisterDto.getAddress().getCity());
        addressModel.setCountry(authRegisterDto.getAddress().getCountry());
        addressModel.setState(authRegisterDto.getAddress().getState());
        addressModel.setStreet(authRegisterDto.getAddress().getStreet());
        addressModel.setZipCode(authRegisterDto.getAddress().getZipCode());
        return addressModel;
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
