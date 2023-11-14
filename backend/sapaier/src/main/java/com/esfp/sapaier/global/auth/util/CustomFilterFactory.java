package com.esfp.sapaier.global.auth.util;

import org.springframework.stereotype.Component;

import com.esfp.sapaier.global.auth.filter.JwtAuthenticationFilter;
import com.esfp.sapaier.global.auth.repository.OAuth2AuthorizationRequestRepository;
import com.esfp.sapaier.global.auth.repository.UserAuthRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomFilterFactory {

	private final JwtTokenProvider jwtTokenProvider;
	private final CookieManager cookieManager;
	private final UserAuthRepository userAuthRepository;
	private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

	public JwtAuthenticationFilter createJwtAuthenticationFilter() {
		return new JwtAuthenticationFilter(jwtTokenProvider, cookieManager, userAuthRepository,
			oAuth2AuthorizationRequestRepository);
	}
}
