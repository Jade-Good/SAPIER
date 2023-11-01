package com.esfp.sapaier.global.auth.handler;

import java.io.IOException;
import java.util.NoSuchElementException;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.esfp.sapaier.domain.user.repository.UserRepository;
import com.esfp.sapaier.domain.user.repository.entity.User;
import com.esfp.sapaier.global.auth.model.dto.CookieDto;
import com.esfp.sapaier.global.auth.model.dto.UserAuthDto;
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
			log.info("[OAuth2AuthenticationSuccessHandler] function : onAuthenticationSuccess | error : response is already committed()");
			return;
		}

		User loginUser = loadUserFromAuthentication(authentication);

		JwtToken token = jwtTokenProvider.createToken(
			loginUser.getUuid(),
			loginUser.getRole().name());

		login(loginUser.getUuid(), loginUser.getKey(), token.getRefreshToken());


		CookieDto refreshToken = CookieDto.builder()
			.name(OAuth2AuthorizationRequestRepository.REFRESH_TOKEN)
			.value(token.getRefreshToken())
			.maxAge(JwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME_COOKIE)
			.build();

		CookieDto accessToken = CookieDto.builder()
			.name(OAuth2AuthorizationRequestRepository.ACCESS_TOKEN)
			.value(token.getAccessToken())
			.maxAge(JwtTokenProvider.ACCESS_TOKEN_EXPIRE_TIME_COOKIE)
			.build();

		cookieManager.updateCookie(
			request,
			response,
			refreshToken);

		cookieManager.updateCookie(
			request,
			response,
			accessToken);

		clearAuthenticationAttributes(request, response);

		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) {

		String targetUrl = cookieManager
			.getCookie(request, OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
			.orElse(new Cookie(OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME, getDefaultTargetUrl()))
			.getValue();

		return UriComponentsBuilder.fromUriString(targetUrl)
			.build()
			.toUriString();
	}

	private void login(String userUuid, String userKey, String refreshToken){

		userAuthRepository.save(UserAuth.builder()
			.userUuid(userUuid)
			.userKey(userKey)
			.refreshToken(refreshToken)
			.build());
	}

	private User loadUserFromAuthentication(Authentication authentication){
		String userKey = ((UserPrincipal)authentication.getPrincipal()).getUserKey();
		User user = userRepository.findUserByKey(userKey)
			.orElseThrow(() -> new NoSuchElementException("회원 정보가 존재하지 않습니다"));
		return user;
	}
	protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
		super.clearAuthenticationAttributes(request);
		oAuth2AuthorizationRequestRepository.removeAuthorizationRequestCookies(request, response);
	}

	// private boolean hasAuthority(Collection<? extends GrantedAuthority> authorities, String authority) {
	// 	if (authorities == null) {
	// 		return false;
	// 	}
	//
	// 	for (GrantedAuthority grantedAuthority : authorities) {
	// 		if (authority.equals(grantedAuthority.getAuthority())) {
	// 			return true;
	// 		}
	// 	}
	// 	return false;
	// }
}