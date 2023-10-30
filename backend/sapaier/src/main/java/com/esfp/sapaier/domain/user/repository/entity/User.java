package com.esfp.sapaier.domain.user.repository.entity;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import com.esfp.sapaier.domain.user.model.dto.UserDto;
import com.esfp.sapaier.domain.user.model.vo.Role;
import com.esfp.sapaier.global.auth.model.vo.OAuth2Provider;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Document
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User {

	@MongoId
	private String id;

	@Indexed(unique = true)
	private String uuid;

	private String nickname;

	private String email;

	@Indexed(unique = true)
	private String socialId;

	private OAuth2Provider socialProvider;

	private String profileImageUrl;

	private Long countOfApiRequest;

	private List<String> workspaces;

	private Role role;

	public void addApiRequestCount(){

		Long nextValue = countOfApiRequest + 1;
		if(nextValue >= 100000)
			throw new IllegalArgumentException("API REQUEST 월 최대 가능 호출 수는 10만회 입니다");
		this.countOfApiRequest = nextValue;
	}

	public void addWorkSpace(String workspaceId){
		this.workspaces.add(workspaceId);
	}

	@Builder
	public User(
		String nickname,
		String email,
		String socialId,
		String profileImageUrl,
		OAuth2Provider socialProvider){

		this.uuid = UUID.randomUUID().toString();
		this.nickname = nickname;
		this.email = email;
		this.socialId = socialId;
		this.socialProvider = socialProvider;
		this.profileImageUrl = profileImageUrl;
		this.countOfApiRequest = 0L;
		this.workspaces = new ArrayList<>();
		this.role = Role.USER;

	}

	public UserDto convertToDto(){
		return UserDto.builder()
			.uuid(this.uuid)
			.nickname(this.nickname)
			.email(this.email)
			.socialId(this.socialId)
			.socialProvider(this.socialProvider)
			.countOfApiRequest(this.countOfApiRequest)
			.workspaces(this.workspaces)
			.profileImageUrl(this.profileImageUrl)
			.build();
	}

}
