package com.esfp.sapaier.global.auth.util;

import org.springframework.stereotype.Component;

import com.esfp.sapaier.global.auth.repository.UserAuthRepository;
import com.esfp.sapaier.global.auth.service.JwtAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomFilterFactory {

	private final JwtTokenProvider jwtTokenProvider;
	private final CookieManager cookieManager;
	private final UserAuthRepository userAuthRepository;

	public JwtAuthenticationFilter createJwtAuthenticationFilter(){
		return new JwtAuthenticationFilter(jwtTokenProvider, cookieManager, userAuthRepository);
	}
}
