package com.esfp.sapaier.global.auth.repository.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import com.esfp.sapaier.global.auth.model.dto.UserAuthDto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
@RedisHash(value = "userAuth")
public class UserAuth {

	@Id
	private String userUuid;
	private String userKey;
	private String refreshToken;

	public UserAuthDto convertToDto(){
		return UserAuthDto.builder()
			.userUuid(userUuid)
			.userKey(userKey)
			.refreshToken(refreshToken)
			.build();
	}
}
