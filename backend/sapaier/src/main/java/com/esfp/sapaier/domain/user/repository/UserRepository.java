package com.esfp.sapaier.domain.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.esfp.sapaier.domain.user.repository.entity.User;
import com.esfp.sapaier.global.auth.model.vo.OAuth2Provider;

public interface UserRepository extends MongoRepository<User, String> {

	@Query("{ socialProvider : ?0, socialId : ?1 }")
	Optional<User> findUserBySocialId(OAuth2Provider provider , String socialId);

	Optional<User> findUserByKey(String id);
}
