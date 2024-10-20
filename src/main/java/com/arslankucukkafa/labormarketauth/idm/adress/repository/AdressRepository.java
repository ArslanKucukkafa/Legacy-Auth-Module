package com.arslankucukkafa.labormarketauth.idm.adress.repository;

import com.arslankucukkafa.labormarketauth.idm.adress.model.AddressModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdressRepository extends MongoRepository<AddressModel,String> {
}
