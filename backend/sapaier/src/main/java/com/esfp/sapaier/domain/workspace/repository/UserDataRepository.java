package com.esfp.sapaier.domain.workspace.repository;

import com.esfp.sapaier.domain.workspace.dto.UserDataDto;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDataRepository extends MongoRepository<UserDataDto, String> {
    @Query("{'uuId': ?0}")
    UserDataDto searchMember(String id);
//    UserDataDto updateFieldName(String id, String newFieldNameValue);
}
