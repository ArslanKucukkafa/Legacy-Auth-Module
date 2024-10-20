package com.arslankucukkafa.labormarketauth.idm.permission.repository;

import com.arslankucukkafa.labormarketauth.idm.permission.model.PermissonModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PermissionRepository extends MongoRepository<PermissonModel, String> {
}
