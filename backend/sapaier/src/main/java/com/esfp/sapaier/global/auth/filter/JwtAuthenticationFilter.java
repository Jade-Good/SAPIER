package com.esfp.sapaier.global.auth.filter;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import com.esfp.sapaier.global.auth.exception.UnAuthorizationException;
import com.esfp.sapaier.global.auth.model.dto.UserAuthDto;
import com.esfp.sapaier.global.auth.model.vo.JwtToken;
import com.esfp.sapaier.global.auth.repository.OAuth2AuthorizationRequestRepository;
import com.esfp.sapaier.global.auth.repository.UserAuthRepository;
import com.esfp.sapaier.global.auth.repository.entity.UserAuth;
import com.esfp.sapaier.global.auth.util.CookieManager;
import com.esfp.sapaier.global.auth.util.JwtTokenProvider;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CookieManager cookieManager;
	private final UserAuthRepository userAuthRepository;
	private final OAuth2AuthorizationRequestRepository oAuth2AuthorizationRequestRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {

		try {
			log.info("[JwtAuthenticationFilter] function : doFilterInternal | message : 토큰 인증 시도");

			String jwtToken = request.getHeader("Authorization");
			String accessToken = "";

			if (jwtToken == null || jwtToken.isEmpty() == true) {

				accessToken = cookieManager
					.getCookie(request, OAuth2AuthorizationRequestRepository.ACCESS_TOKEN)
					.orElseThrow(() -> new NoSuchElementException("Access Token이 존재하지 않습니다"))
					.getValue();
			} else {
				accessToken = jwtTokenProvider.getAccessTokenFromAuthorization(jwtToken);
			}

			Authentication authentication = jwtTokenProvider.getAuthentication(accessToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

			log.info("[JwtAuthenticationFilter] function : doFilterInternal | message : 토큰 인증 완료");

		} catch (ExpiredJwtException e) {

			log.info("[JwtAuthenticationFilter] function : doFilterInternal | message : 토큰 기한 만료");

			JwtToken newToken = refreshToken(request, response);

			Authentication authentication = jwtTokenProvider.getAuthentication(newToken.getAccessToken());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			log.info("[JwtAuthenticationFilter] function : doFilterInternal | message : 토큰 인증 완료");

		} catch (MalformedJwtException e) {
			log.info("[JwtAuthenticationFilter] function : doFilterInternal | error : {}", e.getMessage());
			response.sendError(401, e.getMessage());
		} catch (NoSuchElementException e) {
			log.info("[JwtAuthenticationFilter] function : doFilterInternal | error : {}", e.getMessage());
			response.sendError(401, e.getMessage());
		} catch (Exception e) {
			log.info("[JwtAuthenticationFilter] function : doFilterInternal | error : {}", e.getMessage());
			response.sendError(500, e.getMessage());
		} finally {
			chain.doFilter(request, response);
		}
	}

	private JwtToken refreshToken(HttpServletRequest request, HttpServletResponse response) {

		log.info("[JwtAuthenticationFilter] function : refreshToken | message : 재발급 시도");

		if (request.getHeader("Authorization").isEmpty() != true) {
			log.info("[JwtAuthenticationFilter] function : refreshToken | meesage : 크롬익스텐션은 재발급 불가");
			throw new UnAuthorizationException("로그인이 필요합니다.");
		}

		String refreshTokenInCookie = getRefreshTokenFromCookie(request);

		String userUuid = getUserUuid(refreshTokenInCookie);
		UserAuthDto loginInfo = userAuthRepository.findById(userUuid)
			.orElseThrow(() -> new NoSuchElementException("로그인이 되어있지 않습니다."))
			.convertToDto();
		String refreshTokenInRedis = loginInfo.getRefreshToken();

		log.info("[JwtAuthenticationFilter] function : refreshToken | meesage : 재발급 전 로그인 정보 {}", loginInfo.toString());

		if (Objects.equals(refreshTokenInCookie, refreshTokenInRedis) != true) {
			log.info("[JwtAuthenticationFilter] function : refreshToken | error : Refresh Token 불일치");

			throw new MalformedJwtException("Refresh Token이 유효하지 않습니다");
		}

		JwtToken newToken = jwtTokenProvider.refreshToken(refreshTokenInCookie);

		oAuth2AuthorizationRequestRepository.updateTokenInCookie(request, response, newToken);

		updateLoginInfo(loginInfo, newToken);

		log.info("[JwtAuthenticationFilter] function : refreshToken | message : 재발급 완료 {}", newToken.getAccessToken());

		return newToken;
	}

	private String getRefreshTokenFromCookie(HttpServletRequest request) {

		String refreshToken = cookieManager
			.getCookie(request, OAuth2AuthorizationRequestRepository.REFRESH_TOKEN)
			.orElseThrow(() -> new NoSuchElementException("쿠키에 REFRESH 토큰이 존재하지 않습니다"))
			.getValue();

		return refreshToken;
	}

	private String getUserUuid(String jwtToken) {

		String uuid = jwtTokenProvider
			.parseClaims(jwtToken)
			.getSubject();

		return uuid;
	}

	private void updateLoginInfo(UserAuthDto userAuth, JwtToken newToken) {

		log.info("[JwtAuthenticationFilter] : 기존 로그인 정보 업데이트");

		userAuthRepository.deleteById(userAuth.getUserUuid());

		userAuthRepository.save(UserAuth.builder()
			.userUuid(userAuth.getUserUuid())
			.userKey(userAuth.getUserKey())
			.refreshToken(newToken.getRefreshToken())
			.build());

		UserAuthDto loginInfo = userAuthRepository.findById(userAuth.getUserUuid())
			.orElseThrow(() -> new NoSuchElementException("로그인이 되어있지 않습니다."))
			.convertToDto();

		log.info("[JwtAuthenticationFilter] function : refreshToken | meesage : 재발급 후 로그인 정보 {}", loginInfo.toString());
	}

}
