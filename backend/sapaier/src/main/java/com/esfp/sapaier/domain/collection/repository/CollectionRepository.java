package com.esfp.sapaier.domain.collection.repository;

import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface CollectionRepository extends MongoRepository<CollectionEntity,String> {
    List<CollectionEntity> findByPath(String path);
}
