package com.esfp.sapaier.global.auth.model.dto;

import java.util.Map;

public class OAuth2GithubUserInfoResponse extends OAuth2UserInfoResponse {

	public OAuth2GithubUserInfoResponse(Map<String, Object> attributes) {
		super(attributes);
	}
	@Override
	public String getId() {
		return null;
	}

	@Override
	public String getNickname() {
		return null;
	}

	@Override
	public String getEmail() {
		return null;
	}
}
