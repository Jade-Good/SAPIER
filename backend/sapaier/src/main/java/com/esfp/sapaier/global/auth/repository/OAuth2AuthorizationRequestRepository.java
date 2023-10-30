package com.esfp.sapaier.global.auth.repository;

import java.util.Optional;

import org.springframework.security.oauth2.client.web.AuthorizationRequestRepository;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationRequest;
import org.springframework.stereotype.Repository;

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

	public String OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME = "oauth2AuthRequest";
	public String REDIRECT_URI_PARAM_COOKIE_NAME = "redirectUri";
	public String REFRESH_TOKEN = "refreshToken";
	public int COOKIE_EXPIRE_TIME = 2100000000;


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
			cookieManager.addCookie(
				response,
				OAUTH2_AUTHORIZATION_REQUEST_COOKIE_NAME,
				cookieManager.serialize(authorizationRequest),
				COOKIE_EXPIRE_TIME);

			String redirectUriAfterLogin = request.getParameter(REDIRECT_URI_PARAM_COOKIE_NAME);

			if (StringUtils.isNotBlank(redirectUriAfterLogin)) {
				cookieManager.addCookie(
					response,
					REDIRECT_URI_PARAM_COOKIE_NAME,
					redirectUriAfterLogin,
					COOKIE_EXPIRE_TIME);
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
	}
}
