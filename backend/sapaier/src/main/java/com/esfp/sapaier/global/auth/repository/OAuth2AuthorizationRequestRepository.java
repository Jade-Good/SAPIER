package com.esfp.sapaier.global.auth.repository;

import java.util.Optional;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Repository;

import com.esfp.sapaier.global.auth.model.dto.CookieDto;
import com.esfp.sapaier.global.auth.util.CookieManager;
import com.nimbusds.oauth2.sdk.util.StringUtils;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OAuth2AuthorizationRequestRepository implements
	AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	public static final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2AuthRequest";
	public static final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirectUri";
	public static final String REFRESH_TOKEN = "refreshToken";
	public static final String ACCESS_TOKEN = "accessToken";
	public static final int COOKIE_EXPIRE_TIME = 2100000000;


	private final CookieManager cookieManager;


	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {

		Optional<Cookie> cookie = cookieManager.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);

		if(cookie.isEmpty() == true)
			return null;

		return cookieManager.deserialize(cookie.get(), OAuth2AuthorizationRequest.class);
	}

	@Override
	public void saveAuthorizationRequest(
		OAuth2AuthorizationRequest authorizationRequest,
		HttpServletRequest request,
		HttpServletResponse response) {

		if (authorizationRequest == null) {
			cookieManager.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
			cookieManager.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
			cookieManager.deleteCookie(request, response, REFRESH_TOKEN);

		} else {

			CookieDto cookieDto = CookieDto.builder()
					.name(OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
					.value(cookieManager.serialize(authorizationRequest))
					.maxAge(COOKIE_EXPIRE_TIME)
					.build();

			cookieManager.addCookie(response, cookieDto);

			String redirectUriAfterLogin = request.getParameter("redirect_uri");

			if (StringUtils.isNotBlank(redirectUriAfterLogin)) {

				cookieDto = CookieDto.builder()
					.name(REDIRECT_URI_PARAM_COOKIE_NAME)
					.value(redirectUriAfterLogin)
					.maxAge(COOKIE_EXPIRE_TIME)
					.build();

				cookieManager.addCookie(response, cookieDto);;
			}
		}
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request, HttpServletResponse response) {

		return this.loadAuthorizationRequest(request);
	}

	public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {

		cookieManager.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
		cookieManager.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
		cookieManager.deleteCookie(request, response, REFRESH_TOKEN);
		cookieManager.deleteCookie(request, response, ACCESS_TOKEN);
	}
}
