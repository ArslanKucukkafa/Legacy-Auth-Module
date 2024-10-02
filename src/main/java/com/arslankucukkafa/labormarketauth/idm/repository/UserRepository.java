package com.arslankucukkafa.labormarketauth.idm.repository;

import com.arslankucukkafa.labormarketauth.idm.model.UserModel;
import org.apache.catalina.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findUserModelById(String id);
    Optional<UserModel> findUserModelByUsername(String username);

    //todo: Burda email contact içerisinde. Yeniden düzenlenmesi gerekiyor.
    Optional<UserModel> findUserModelByContact(String contact);
    List<UserModel> findAll();
}
