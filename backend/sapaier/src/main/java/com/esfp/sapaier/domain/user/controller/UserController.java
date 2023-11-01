package com.esfp.sapaier.domain.user.controller;

import java.util.NoSuchElementException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.esfp.sapaier.domain.user.model.dto.UserDto;
import com.esfp.sapaier.domain.user.repository.UserRepository;
import com.esfp.sapaier.domain.user.service.UserService;
import com.esfp.sapaier.global.auth.model.dto.UserAuthDto;
import com.esfp.sapaier.global.auth.repository.UserAuthRepository;
import com.esfp.sapaier.global.auth.service.UserAuthService;
import com.esfp.sapaier.global.auth.util.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class UserController {

	private final UserAuthService userAuthService;
	private final UserService userService;
	private final JwtTokenProvider jwtTokenProvider;

	@GetMapping("/users")
	public ResponseEntity getUserInfo(@CookieValue String accessToken){

		log.info("[UserController] function : getUserInfo | message : 유저 정보 요청");

		String userUuid = jwtTokenProvider.parseClaims(accessToken).getSubject();
		String userKey = userAuthService.getUserKeyFromUuid(userUuid);

		UserDto userDto = userService.getUserInfo(userKey);

		return ResponseEntity.ok().body(userDto);
	}

}
