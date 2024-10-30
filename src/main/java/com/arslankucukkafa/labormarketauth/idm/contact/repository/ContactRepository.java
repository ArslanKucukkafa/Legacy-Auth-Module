package com.arslankucukkafa.labormarketauth.idm.contact.repository;

import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ContactRepository extends MongoRepository<ContactModel, String> {
    Optional<ContactModel> findByEmail(String email);

    Optional<ContactModel> findById(String id);

    @Query("{ '$or': [ { 'email': ?0 }, { 'phone': ?1 }, { 'github_url': ?2 }, { 'avatar_url': ?3 } ] }")
    Optional<ContactModel> findByContactInfo(String email, String phone, String githubUrl, String avatarUrl);
}
