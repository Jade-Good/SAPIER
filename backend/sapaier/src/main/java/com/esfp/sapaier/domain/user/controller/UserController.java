package com.esfp.sapaier.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esfp.sapaier.domain.user.model.dto.UserDto;
import com.esfp.sapaier.domain.user.service.UserService;
import com.esfp.sapaier.global.auth.service.UserAuthService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

	private final UserAuthService userAuthService;
	private final UserService userService;

	@GetMapping("/users")
	public ResponseEntity getUserInfo(
		@RequestHeader(name = "Authorization", required = false) String bearerToken,
		@CookieValue(name = "accessToken", required = false) String accessToken) {

		log.info("[UserController] function : getUserInfo | message : 유저 정보 요청");

		String userKey = userAuthService.getUserKeyFromUuid(accessToken, bearerToken);

		UserDto userDto = userService.getUserInfo(userKey);

		log.info("[getUserInfo] 유저 정보 : {}", userDto.toString());
		return ResponseEntity.ok().body(userDto);
	}

}
