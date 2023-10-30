package com.esfp.sapaier.global.auth.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.esfp.sapaier.global.auth.repository.entity.UserAuth;

@Repository
public interface UserAuthRepository extends CrudRepository<UserAuth, String> {
}
