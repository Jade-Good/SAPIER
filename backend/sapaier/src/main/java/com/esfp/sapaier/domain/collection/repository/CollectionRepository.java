package com.esfp.sapaier.domain.collection.repository;

import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CollectionRepository extends MongoRepository<CollectionEntity,String> {
    Optional<CollectionEntity> findTop1ByOrderByCreatedTimeDesc();
}
