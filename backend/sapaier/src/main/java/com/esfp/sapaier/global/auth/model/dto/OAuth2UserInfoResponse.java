package com.esfp.sapaier.global.auth.model.dto;

import java.util.Map;
import java.util.Optional;

import com.esfp.sapaier.global.auth.model.vo.OAuth2Provider;

public abstract class OAuth2UserInfoResponse {

	protected Map<String, Object> attributes;

	public static Optional<OAuth2UserInfoResponse> getOAuth2UserInfoResponse(OAuth2Provider socialProvider, Map<String, Object> attributes){
		if(socialProvider.equals(OAuth2Provider.GITHUB))
			return Optional.of(new OAuth2GithubUserInfoResponse(attributes));
		if(socialProvider.equals(OAuth2Provider.GOOGLE))
			return Optional.of(new OAuth2GoogleUserInfoResponse(attributes));
		return Optional.empty();
	}

	public OAuth2UserInfoResponse(Map<String, Object> attributes) {
		this.attributes = attributes;
	}

	public Map<String, Object> getAttributes() {
		return attributes;
	}

	public abstract String getId();

	public abstract String getNickname();

	public abstract String getEmail();

}