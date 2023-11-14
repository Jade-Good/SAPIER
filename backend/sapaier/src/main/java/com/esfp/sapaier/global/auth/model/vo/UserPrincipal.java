package com.esfp.sapaier.global.auth.model.vo;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPrincipal implements OidcUser, OAuth2User {

	private final String name;
	private final Map<String, Object> attributes;
	private final OidcIdToken idToken;
	private final Collection<GrantedAuthority> authorities;
	private final Map<String, Object> claims;
	private final OidcUserInfo userInfo;

	@Builder
	public UserPrincipal(String key, String role, Map<String, Object> attributes) {
		this.name = key;
		this.idToken = null;
		this.attributes = attributes;
		this.authorities = Collections.singletonList(new SimpleGrantedAuthority(role));
		this.claims = null;
		this.userInfo = null;
	}

	public String getUserKey() {
		return this.name;
	}
}