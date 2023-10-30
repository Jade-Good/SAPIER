package com.esfp.sapaier.global.auth.model.vo;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.oauth2.core.oidc.OidcIdToken;
import org.springframework.security.oauth2.core.oidc.OidcUserInfo;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.user.OAuth2User;

import com.esfp.sapaier.domain.user.model.dto.UserDto;
import com.esfp.sapaier.domain.user.repository.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
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
	public UserPrincipal (UserDto userInfo, Map<String, Object> attributes){
		this.name = userInfo.getSocialId();
		this.idToken = null;
		this.attributes = attributes;
		this.authorities = Collections.singletonList(new SimpleGrantedAuthority(userInfo.getRole().name()));
		this.claims = new HashMap<>();
		this.userInfo = new OidcUserInfo(claims);
	}
}