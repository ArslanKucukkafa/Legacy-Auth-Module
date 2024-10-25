package com.arslankucukkafa.labormarketauth.idm.role.repository;

import com.arslankucukkafa.labormarketauth.idm.role.model.RoleModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends MongoRepository<RoleModel, String> {
    Optional<RoleModel> findByName(String name);

}
