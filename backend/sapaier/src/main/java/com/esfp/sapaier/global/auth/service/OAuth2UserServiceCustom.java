package com.esfp.sapaier.global.auth.service;

import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import com.esfp.sapaier.domain.user.model.dto.UserDto;
import com.esfp.sapaier.domain.user.repository.UserRepository;
import com.esfp.sapaier.domain.user.repository.entity.User;
import com.esfp.sapaier.global.auth.model.dto.OAuth2UserInfoResponse;
import com.esfp.sapaier.global.auth.model.vo.UserPrincipal;
import com.esfp.sapaier.global.auth.model.vo.OAuth2Provider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OAuth2UserServiceCustom extends DefaultOAuth2UserService {

	private final UserRepository userRepository;

	@Override
	public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
		OAuth2User oAuth2user = super.loadUser(userRequest);

		try {
			OAuth2Provider oAuth2Provider = OAuth2Provider
				.valueOf(userRequest.getClientRegistration().getRegistrationId().toUpperCase());

			OAuth2UserInfoResponse oAuth2UserInfoResponse = OAuth2UserInfoResponse
				.getOAuth2UserInfoResponse(oAuth2Provider, oAuth2user.getAttributes())
				.orElseThrow(() -> new IllegalArgumentException(""));

			UserDto userDto = userRepository.findUserBySocialId(oAuth2UserInfoResponse.getId())
				.orElseGet(() -> registerUser(oAuth2Provider, oAuth2UserInfoResponse))
				.convertToDto();

			return new UserPrincipal(userDto, oAuth2user.getAttributes());

		} catch (Exception ex) {
			log.error("CustomOAuth2UserService loadUser Error {} ", ex.getMessage());
			throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
		}
	}

	private User registerUser(OAuth2Provider oAuth2Provider, OAuth2UserInfoResponse oAuth2UserInfoResponse){

		String profileImageUrl = "";
		if(oAuth2Provider.equals(OAuth2Provider.GITHUB))
			profileImageUrl = oAuth2UserInfoResponse.getAttributes().get("avatar_url").toString();
		if(oAuth2Provider.equals(OAuth2Provider.GOOGLE))
			profileImageUrl = oAuth2UserInfoResponse.getAttributes().get("picture").toString();


		User newUser =  User.builder()
			.email(oAuth2UserInfoResponse.getEmail())
			.nickname(oAuth2UserInfoResponse.getNickname())
			.socialProvider(oAuth2Provider)
			.socialId(oAuth2UserInfoResponse.getId())
			.profileImageUrl(profileImageUrl)
			.build();

		userRepository.save(newUser);

		return newUser;
	}

}
