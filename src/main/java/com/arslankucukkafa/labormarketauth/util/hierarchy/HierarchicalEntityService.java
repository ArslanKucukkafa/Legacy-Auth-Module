package com.arslankucukkafa.labormarketauth.util.hierarchy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

public abstract class HierarchicalEntityService<T extends HierarchicalEntity<T>> {

/*    @Autowired
    private MongoTemplate mongoTemplate;

    public void delete(Long id) {
        T entity = mongoTemplate.findById(id).orElseThrow(() -> new EntityNotFoundException());
        if (!entity.getChildren().isEmpty()) {
            throw new IllegalStateException("Parent cannot be deleted while it has children.");
        }
        repository.delete(entity);
    }*/

}
