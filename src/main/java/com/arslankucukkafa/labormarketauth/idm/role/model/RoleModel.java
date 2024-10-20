package com.arslankucukkafa.labormarketauth.idm.role.model;

import com.arslankucukkafa.labormarketauth.idm.permission.model.PermissonModel;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
public class RoleModel {

    @Id
    private String id;
    private String name;
    private String description;
/*
    fixme: Burda permission için referans tutsam mı bilemedim. Çünkü endpoint pathleri değişebilir
     . Ama bu durumda sorgu için yapılan repository işlemleri biraz daha karmaşık hale gelebilir.
     Ayrıca SecurityContextHolder dan direk olarak permissionları alınamayan bir yapı oluşur.
*/
    private List<PermissonModel> permissons;
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

    public List<PermissonModel> getPermissons() {
        return permissons;
    }

    public void setPermissons(List<PermissonModel> permissons) {
        this.permissons = permissons;
    }

    public void addPermissons(PermissonModel permissons) {
        this.permissons.add(permissons);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
