package com.esfp.sapaier.domain.user.model.dto;

import java.util.List;

import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.MongoId;
import org.springframework.stereotype.Service;

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

	private Long countOfApiRequest;

	private List<String> workspaces;
}
