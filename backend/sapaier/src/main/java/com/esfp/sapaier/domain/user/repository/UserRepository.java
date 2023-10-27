package com.esfp.sapaier.domain.user.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.esfp.sapaier.domain.user.repository.entity.User;

public interface UserRepository extends MongoRepository<User, String> {

	Optional<User> findUserBySocialId(String socialId);
	Optional<User> findUserByUuid(String uuid);
	Optional<User> findUserById(String id);
}
