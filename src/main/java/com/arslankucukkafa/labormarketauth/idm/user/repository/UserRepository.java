package com.arslankucukkafa.labormarketauth.idm.user.repository;

import com.arslankucukkafa.labormarketauth.idm.user.model.UserModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends MongoRepository<UserModel, String> {
    Optional<UserModel> findUserModelById(String id);
    Optional<UserModel> findUserModelByUsername(String username);

    //todo: Burda email contact içerisinde. Yeniden düzenlenmesi gerekiyor.
    @Query("{'contact.email': ?0}")
    Optional<UserModel> findUserModelByContactEmail(String contact);
    List<UserModel> findAll();
}
