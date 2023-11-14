package com.esfp.sapaier.global.auth.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.esfp.sapaier.global.auth.model.dto.UserAuthDto;
import com.esfp.sapaier.global.auth.repository.UserAuthRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserAuthService {

	private final UserAuthRepository userAuthRepository;

	public UserAuthDto getUserAuthInfo(String userUuid) {
		UserAuthDto userAuthDto = userAuthRepository
			.findById(userUuid)
			.orElseThrow(() -> new NoSuchElementException("로그인 되어있지 않습니다"))
			.convertToDto();
		return userAuthDto;
	}

	public String getUserKeyFromUuid(String userUuid) {
		return getUserAuthInfo(userUuid).getUserKey();
	}
}
