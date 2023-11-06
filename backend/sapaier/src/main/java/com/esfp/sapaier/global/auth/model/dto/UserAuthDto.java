package com.esfp.sapaier.global.auth.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserAuthDto {

	private String userUuid;
	private String userKey;
	private String refreshToken;

	@Override
	public String toString() {
		return "UserAuthDto{" +
			"userUuid='" + userUuid + '\'' +
			", userKey='" + userKey + '\'' +
			", refreshToken='" + refreshToken + '\'' +
			'}';
	}
}
