package com.esfp.sapaier.global.auth.model.dto;

import java.util.Map;

public class OAuth2GoogleUserInfoResponse extends OAuth2UserInfoResponse {
	public OAuth2GoogleUserInfoResponse(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return (String)attributes.get("sub");
	}

	@Override
	public String getNickname() {
		return (String)attributes.get("name");
	}

	@Override
	public String getEmail() {
		return (String)attributes.get("email");
	}
}
