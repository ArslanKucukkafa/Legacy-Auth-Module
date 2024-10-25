package com.arslankucukkafa.labormarketauth.idm.user.model;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import com.arslankucukkafa.labormarketauth.idm.auth.model.sync.Provider;
import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import jakarta.annotation.Nullable;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Document
public class UserModel implements UserDetails {
    @Id
    private String id;
    @Indexed(unique = true)
    private String username;
    @Nullable
    private String password;
    private String name;
    private String surname;
    // todo: Bunun tipini degiştirmemiz lazım
    private String birthDate;
    // todo: Bu tc vatandaşlıgı doğrulaması sırasında set edilecek
    private boolean isAccountVerified;
    // todo: update işlemlerinde version kontrolü yapılacak
    private int version;
/*
    arslan.kucukkafa: Bu kullanıcının daha önceden hangi oauth sağlayıcısı ile giriş yaptığını tutar.
     Her saglayacı email dışında farklı bilgiler sakladıgından dolayı, farklı oauth saglayıcılarından gelen bilgileri birleştirmek ve db'yi zenginleştirmeliyiz.
*/
    @Nullable
    private List<Provider> provider;
    @DBRef
    private ContactModel contact;
    @DBRef
    private AddressModel address;
    @DBRef
    private List<RoleModel> role;



    public String getUsername() {
        return username;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<RoleModel> role = getRoles();
        List<SimpleGrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (RoleModel roleModel : role) {
            grantedAuthorities.add(new SimpleGrantedAuthority(roleModel.getName()));
        }
        return grantedAuthorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public List<RoleModel> getRoles() {
        return role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public void setRole(List<RoleModel> role) {
        this.role = role;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public boolean isAccountVerified() {
        return isAccountVerified;
    }

    public void setAccountVerified(boolean accountVerified) {
        isAccountVerified = accountVerified;
    }

    public List<Provider> getProvider() {
        return provider;
    }

    public void setProvider(List<Provider> provider) {
        this.provider = provider;
    }
}
