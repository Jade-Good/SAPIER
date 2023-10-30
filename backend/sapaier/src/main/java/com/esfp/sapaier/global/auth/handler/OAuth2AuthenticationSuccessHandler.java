package com.esfp.sapaier.global.auth.handler;

import java.io.IOException;
import java.util.Collection;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import com.esfp.sapaier.domain.user.repository.UserRepository;
import com.esfp.sapaier.domain.user.repository.entity.User;
import com.esfp.sapaier.global.auth.model.dto.OAuth2UserInfoResponse;
import com.esfp.sapaier.global.auth.model.vo.JwtToken;
import com.esfp.sapaier.global.auth.model.vo.OAuth2Provider;
import com.esfp.sapaier.global.auth.model.vo.UserPrincipal;
import com.esfp.sapaier.global.auth.repository.OAuth2AuthorizationRequestRepository;
import com.esfp.sapaier.global.auth.util.CookieManager;
import com.esfp.sapaier.global.auth.util.JwtTokenProvider;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;

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
	private final UserRepository memberRepository;
	private final CookieManager cookieManager;

	@Override
	public void onAuthenticationSuccess(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) throws IOException {

		String targetUrl = determineTargetUrl(request, response, authentication);

		if (response.isCommitted()) {
			log.error("Response has already been committed. Unable to redirect to " + targetUrl);
			return;
		}

		clearAuthenticationAttributes(request, response);
		getRedirectStrategy().sendRedirect(request, response, targetUrl);
	}

	protected String determineTargetUrl(
		HttpServletRequest request,
		HttpServletResponse response,
		Authentication authentication) {

		String targetUrl = cookieManager
			.getCookie(request, OAuth2AuthorizationRequestRepository.REDIRECT_URI_PARAM_COOKIE_NAME)
			.orElse(new Cookie("redirectUri", getDefaultTargetUrl()))
			.getValue();

		UserPrincipal loginUserPrincipal = ((UserPrincipal)authentication.getPrincipal());

		User loginUser = memberRepository.findUserBySocialId(loginUserPrincipal.getName())
			.orElseThrow(() -> new NoSuchElementException("해당 사용자가 존재하지 않습니다."));

		JwtToken tokenInfo = jwtTokenProvider.createToken(
			loginUser.getUuid(),
			loginUser.getRole().name());

		log.info("successHanlder : " + loginUser.getUuid());

		cookieManager.deleteCookie(request, response, OAuth2AuthorizationRequestRepository.REFRESH_TOKEN);
		cookieManager.addCookie(
			response,
			OAuth2AuthorizationRequestRepository.REFRESH_TOKEN,
			tokenInfo.getRefreshToken(),
			JwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME_COOKIE);

		// memberAuthService.login(
		// 	MemberAuthDto.builder()
		// 		.memberUUID(bySocialId.getUuid())
		// 		.memberIdx(bySocialId.getIdx())
		// 		.memberRefreshToken(tokenInfo.getRefreshToken())
		// 		.build());

		return UriComponentsBuilder.fromUriString(targetUrl)
			.queryParam("accessToken", tokenInfo.getAccessToken())
			.queryParam("refreshToken", tokenInfo.getRefreshToken())
			.build()
			.toUriString();
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