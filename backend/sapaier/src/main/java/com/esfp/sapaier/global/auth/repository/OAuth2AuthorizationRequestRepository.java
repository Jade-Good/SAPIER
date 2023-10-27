package com.esfp.sapaier.global.auth.repository;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Repository;

import com.esfp.sapaier.global.auth.util.CookieManager;
import com.nimbusds.oauth2.sdk.util.StringUtils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;

@Repository
@RequiredArgsConstructor
public class OAuth2AuthorizationRequestRepository implements
	AuthorizationRequestRepository<OAuth2AuthorizationRequest> {

	private final CookieManager cookieManager;


	private final String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2AuthRequest";
	private final String REDIRECT_URI_PARAM_COOKIE_NAME = "redirectUri";
	private final String REFRESH_TOKEN = "refreshToken";
	private final int COOKIE_EXPIRE_TIME = 2100000000;

	@Override
	public OAuth2AuthorizationRequest loadAuthorizationRequest(HttpServletRequest request) {
		return cookieManager.getCookie(request, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME)
			.map(cookie -> cookieManager.deserialize(cookie, OAuth2AuthorizationRequest.class))
			.orElse(null);
	}

	@Override
	public void saveAuthorizationRequest(OAuth2AuthorizationRequest authorizationRequest, HttpServletRequest request,
		HttpServletResponse response) {
		if (authorizationRequest == null) {
			cookieManager.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
			cookieManager.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
			cookieManager.deleteCookie(request, response, REFRESH_TOKEN);
			return;
		}

		cookieManager.addCookie(response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
			cookieManager.serialize(authorizationRequest), COOKIE_EXPIRE_TIME);
		String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);
		if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
			cookieManager.addCookie(response, REDIRECT_URI_PARAM_COOKIE_NAME, redirectUriAfterLogin, COOKIE_EXPIRE_TIME);
		}
	}

	@Override
	public OAuth2AuthorizationRequest removeAuthorizationRequest(HttpServletRequest request,
		HttpServletResponse response) {
		return this.loadAuthorizationRequest(request);
	}

	public void removeAuthorizationRequestCookies(HttpServletRequest request, HttpServletResponse response) {
		cookieManager.deleteCookie(request, response, OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME);
		cookieManager.deleteCookie(request, response, REDIRECT_URI_PARAM_COOKIE_NAME);
		cookieManager.deleteCookie(request, response, REFRESH_TOKEN);
	}
}
