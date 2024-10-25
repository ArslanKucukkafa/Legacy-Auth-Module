package com.arslankucukkafa.labormarketauth.util.hierarchy;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HierarchicalRepository extends MongoRepository<HierarchicalEntity,Long> {
}
