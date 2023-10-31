package com.esfp.sapaier.domain.request.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.esfp.sapaier.domain.request.repository.Entity.Collection;

@Repository
public interface RequestRepository extends MongoRepository<Collection, String> {
}
