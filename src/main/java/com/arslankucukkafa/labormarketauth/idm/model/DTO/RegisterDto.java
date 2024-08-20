package com.arslankucukkafa.labormarketauth.idm.model.DTO;

import com.arslankucukkafa.labormarketauth.idm.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.model.UserModel;

public class RegisterDto {
    private String nqme;
    private String surname;
    private String username;
    private String birthDate;
    private ContactModel contact;
    private AddressModel address;


    public RegisterDto(String nqme, String surname, String username, String birthDate, ContactModel contact, AddressModel address) {
        this.nqme = nqme;
        this.surname = surname;
        this.username = username;
        this.birthDate = birthDate;
        this.contact = contact;
        this.address = address;
    }

    public RegisterDto() {
    }

    public UserModel toUserModel() {
        UserModel userModel = new UserModel();
        userModel.setName(nqme);
        userModel.setSurname(surname);
        userModel.setUsername(username);
        userModel.setBirthDate(birthDate);
        userModel.setContact(contact);
        userModel.setAddress(address);
        return userModel;
    }

    public String getNqme() {
        return nqme;
    }

    public void setNqme(String nqme) {
        this.nqme = nqme;
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
}
