package com.esfp.sapaier.global.auth.model.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class JwtToken {
	private String grantType;
	private String accessToken;
	private String refreshToken;
	private Long expireTime;
}
