package com.esfp.sapaier.domain.collection.repository;

import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CollectionRepository extends MongoRepository<CollectionEntity,String> {
}
