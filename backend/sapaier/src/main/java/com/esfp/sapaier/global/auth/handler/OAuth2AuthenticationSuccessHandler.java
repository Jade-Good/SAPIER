package com.esfp.sapaier.global.auth.handler;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.esfp.sapaier.domain.user.repository.UserRepository;
import com.esfp.sapaier.domain.user.repository.entity.User;
import com.esfp.sapaier.global.auth.model.vo.JwtToken;
import com.esfp.sapaier.global.auth.model.vo.UserPrincipal;
import com.esfp.sapaier.global.auth.repository.OAuth2AuthorizationRequestRepository;
import com.esfp.sapaier.global.auth.repository.UserAuthRepository;
import com.esfp.sapaier.global.auth.repository.entity.UserAuth;
import com.esfp.sapaier.global.auth.util.CookieManager;
import com.esfp.sapaier.global.auth.util.JwtTokenProvider;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

	private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

	private final JwtTokenProvider jwtTokenProvider;
	private final CookieManager cookieManager;

	private final UserRepository userRepository;
	private final UserAuthRepository userAuthRepository;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) throws IOException {

		log.info("[OAuth2AuthenticationSuccessHandler] function : onAuthenticationSuccess | message : 인증 성공 리다이렉트");

		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			log.info(
				"[OAuth2AuthenticationSuccessHandler] function : onAuthenticationSuccess | error : response is already committed()");
			return;
		}

		User loginUser = loadUserFromAuthentication(authentication);

		if (isFromExtension(targetUrl) != true) {
			JwtToken token = jwtTokenProvider.createToken(
				loginUser.getUuid(),
				loginUser.getRole().name());

			login(loginUser.getUuid(), loginUser.getKey(), token.getRefreshToken());

			clearAuthenticationAttributes(request, response);

			oAuth2AuthorizationRequestRepository.updateTokenInCookie(request, response, token);

			getRedirectStrategy().sendRedirect(request, response, targetUrl);

		} else {
			JwtToken token = jwtTokenProvider.createTokenFroExtension(
				loginUser.getUuid(),
				loginUser.getRole().name());

			login(loginUser.getUuid(), loginUser.getKey(), token.getRefreshToken());

			clearAuthenticationAttributes(request, response);

			targetUrl = UriComponentsBuilder
				.fromUriString(targetUrl)
				.queryParam("accessToken", token.getAccessToken())
				.toUriString();

			getRedirectStrategy().sendRedirect(request, response, targetUrl);
		}

	}

	protected String determineTargetUrl(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) {

		String targetUrl = cookieManager
			.getCookie(request, OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
			.orElse(
				new Cookie(OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME, getDefaultTargetUrl()))
			.getValue();

		return UriComponentsBuilder
			.fromUriString(targetUrl)
			.build()
			.toUriString();
	}

	private void login(String userUuid, String userKey, String refreshToken) {

		userAuthRepository.save(UserAuth.builder()
			.userUuid(userUuid)
			.userKey(userKey)
			.refreshToken(refreshToken)
			.build());
	}

	private User loadUserFromAuthentication(Authentication authentication) {
		String userKey = ((UserPrincipal)authentication.getPrincipal()).getUserKey();
		User user = userRepository.findUserByKey(userKey)
			.orElseThrow(() -> new NoSuchElementException("회원 정보가 존재하지 않습니다"));
		return user;
	}

	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		oAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	private boolean isFromExtension(String redirectUrl) throws MalformedURLException {
		UriComponentsBuilder uri = UriComponentsBuilder.fromUriString(redirectUrl);
		MultiValueMap<String, String> map = uri.build().getQueryParams();

		String value = map.getFirst("forExtension");
		if (value != null && value.equals("Y") == true)
			return true;
		else
			return false;
	}
}