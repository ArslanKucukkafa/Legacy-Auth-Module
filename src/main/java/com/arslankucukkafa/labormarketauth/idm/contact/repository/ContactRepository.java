package com.arslankucukkafa.labormarketauth.idm.contact.repository;

import com.arslankucukkafa.labormarketauth.idm.contact.model.ContactModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactRepository extends MongoRepository<ContactModel, String> {
}
