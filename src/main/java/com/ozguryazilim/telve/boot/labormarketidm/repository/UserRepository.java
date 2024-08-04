package com.ozguryazilim.telve.boot.labormarketidm.repository;

import com.ozguryazilim.telve.boot.labormarketidm.model.UserModel;
import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

@EnableScan
public interface UserRepository extends CrudRepository<UserModel, String> {
    Optional<UserModel> findUserModelById(String id);
}
