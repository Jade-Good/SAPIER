package com.esfp.sapaier.global.auth.model.dto;

import java.util.Map;

public class OAuth2GoogleUserInfoResponse extends OAuth2UserInfoResponse {
	public OAuth2GoogleUserInfoResponse(Map<String, Object> attributes) {
		super(attributes);
	}

	@Override
	public String getId() {
		return attributes.get("sub").toString();
	}

	@Override
	public String getNickname() {return attributes.get("name").toString();}

	@Override
	public String getEmail() { return attributes.get("email").toString();}
}
