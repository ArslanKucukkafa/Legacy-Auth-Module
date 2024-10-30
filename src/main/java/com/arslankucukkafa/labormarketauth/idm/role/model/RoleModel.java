package com.arslankucukkafa.labormarketauth.idm.role.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.util.List;

@Document
public class RoleModel implements Serializable {

    @Id
    private String id;
    @Indexed(unique = true)
    private String name;
    private String description;
/*
    fixme: Burda permission için referans tutsam mı bilemedim. Çünkü endpoint pathleri değişebilir
     . Ama bu durumda sorgu için yapılan repository işlemleri biraz daha karmaşık hale gelebilir.
     Ayrıca SecurityContextHolder dan direk olarak permissionları alınamayan bir yapı oluşur.
*/
    private List<Permission> permissions;
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Permission> getPermissons() {
        return permissions;
    }

    public void setPermissons(List<Permission> permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}

