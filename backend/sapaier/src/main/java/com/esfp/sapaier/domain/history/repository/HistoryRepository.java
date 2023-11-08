package com.esfp.sapaier.domain.history.repository;

import com.esfp.sapaier.domain.history.repository.entity.HistoryEntity;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface HistoryRepository extends MongoRepository<HistoryEntity, String> {
    List<HistoryEntity> findAllByUuidOrderByCreatedTimeDesc(String Uuid);
}
