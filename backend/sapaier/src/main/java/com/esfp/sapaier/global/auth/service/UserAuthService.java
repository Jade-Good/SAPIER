package com.esfp.sapaier.global.auth.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.esfp.sapaier.global.auth.model.dto.UserAuthDto;
import com.esfp.sapaier.global.auth.repository.UserAuthRepository;
import com.esfp.sapaier.global.auth.util.JwtTokenProvider;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {

	private final UserAuthRepository userAuthRepository;
	private final JwtTokenProvider jwtTokenProvider;

	public String getUserKeyFromUuid(String accessToken, String bearerToken) {
		String userUuid = getUserUuid(accessToken, bearerToken);
		return getUserAuthInfo(userUuid).getUserKey();
	}

	private String getUserUuid(String accessToken, String bearerToken) {
		if (accessToken != null) {
			return jwtTokenProvider.parseClaims(accessToken).getSubject();
		} else if (bearerToken != null) {
			accessToken = jwtTokenProvider.getAccessTokenFromAuthorization(bearerToken);
			return jwtTokenProvider.parseClaims(accessToken).getSubject();
		} else {
			throw new NoSuchElementException("토큰이 존재하지 않습니다");
		}
	}

	public UserAuthDto getUserAuthInfo(String userUuid) {
		UserAuthDto userAuthDto = userAuthRepository
			.findById(userUuid)
			.orElseThrow(() -> new NoSuchElementException("로그인 되어있지 않습니다"))
			.convertToDto();
		return userAuthDto;
	}

}
