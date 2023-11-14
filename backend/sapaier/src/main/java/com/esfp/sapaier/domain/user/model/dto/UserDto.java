package com.esfp.sapaier.domain.user.model.dto;

import java.util.List;

import com.esfp.sapaier.domain.user.model.vo.Role;
import com.esfp.sapaier.global.auth.model.vo.OAuth2Provider;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class UserDto {

	private String uuid;

	private String nickname;

	private String email;

	private String socialId;

	private OAuth2Provider socialProvider;

	private String profileImageUrl;

	private Long countOfApiRequest;

	private List<String> workspaces;

	private Role role;

	@Override
	public String toString() {
		return "UserDto{" +
			"uuid='" + uuid + '\'' +
			", nickname='" + nickname + '\'' +
			", email='" + email + '\'' +
			", socialId='" + socialId + '\'' +
			", socialProvider=" + socialProvider +
			", profileImageUrl='" + profileImageUrl + '\'' +
			", countOfApiRequest=" + countOfApiRequest +
			", workspaces=" + workspaces +
			", role=" + role +
			'}';
	}
}
