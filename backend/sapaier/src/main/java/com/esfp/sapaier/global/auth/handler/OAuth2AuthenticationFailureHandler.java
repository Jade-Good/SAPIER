package com.esfp.sapaier.global.auth.handler;

import java.io.IOException;
import java.util.Optional;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.esfp.sapaier.global.auth.repository.OAuth2AuthorizationRequestRepository;
import com.esfp.sapaier.global.auth.util.CookieManager;


import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {

	private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;
	private final CookieManager cookieManager;

	@Override
	public void onAuthenticationFailure(
		HttpServletRequest request,
		HttpServletResponse response,
		AuthenticationException exception) throws IOException {


		String targetUrl = cookieManager
			.getCookie(request, OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
			.orElse(new Cookie("redirectUri","/"))
			.getValue();


		log.error("OAuth2 에러 : {} ", exception.getMessage());

		targetUrl = UriComponentsBuilder
			.fromUriString(targetUrl)
			.queryParam("error", exception.getLocalizedMessage())
			.build()
			.toUriString();

		oAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}
}
