package com.esfp.sapaier.global.auth.service;

import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.StringTokenizer;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import com.esfp.sapaier.global.auth.model.dto.UserAuthDto;
import com.esfp.sapaier.global.auth.model.vo.JwtToken;
import com.esfp.sapaier.global.auth.repository.OAuth2AuthorizationRequestRepository;
import com.esfp.sapaier.global.auth.repository.UserAuthRepository;
import com.esfp.sapaier.global.auth.repository.entity.UserAuth;
import com.esfp.sapaier.global.auth.util.CookieManager;
import com.esfp.sapaier.global.auth.util.JwtTokenProvider;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final JwtTokenProvider jwtTokenProvider;
	private final CookieManager cookieManager;
	private final UserAuthRepository userAuthRepository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws
		ServletException,
		IOException {

		try {
			log.info("JWT Authentication Filter : START");

			String jwtToken = jwtTokenProvider.resolveToken(request.getHeader("Authorization"));
			
			log.info("JWT Authentication Filter : 토큰 인증 성공");

			Authentication authentication = jwtTokenProvider.getAuthentication(jwtToken);
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (ExpiredJwtException e) {

			log.info("JWT Authentication Filter : 토큰 기한 만료");

			JwtToken newToken = refreshToken(request);

			cookieManager.deleteCookie(
				request,
				response,
				OAuth2AuthorizationRequestRepository.REFRESH_TOKEN);

			cookieManager.addCookie(
				response,
				OAuth2AuthorizationRequestRepository.REFRESH_TOKEN,
				newToken.getRefreshToken(),
				JwtTokenProvider.REFRESH_TOKEN_EXPIRE_TIME_COOKIE);

			response.setHeader("x-access-token", newToken.getAccessToken());

			Authentication authentication = jwtTokenProvider.getAuthentication(newToken.getAccessToken());
			SecurityContextHolder.getContext().setAuthentication(authentication);

		} catch (MalformedJwtException e) {
			log.info(e.getMessage());
			response.sendError(401, e.getMessage());
		} catch (NoSuchElementException e) {
			log.info(e.getMessage());
			response.sendError(401, e.getMessage());
		} catch (Exception e) {
			log.info(e.getMessage());
			response.sendError(500, e.getMessage());
		} finally {
			log.info("JWT Authentication Filter : END");
			chain.doFilter(request, response);
		}
	}



	private JwtToken refreshToken(HttpServletRequest request) {

		log.info("JWT Authentication Filter : 토큰 재발급 시도");


		String refreshTokenInCookie = getRefreshTokenFromCookie(request);

		String userUuid = getUserUuid(refreshTokenInCookie);
		UserAuthDto loginInfo = userAuthRepository.findById(userUuid)
			.orElseThrow(() -> new NoSuchElementException("로그인이 되어있지 않습니다."))
			.convertToDto();
		String refreshTokenInRedis = loginInfo.getRefreshToken();


		if(Objects.equals(refreshTokenInCookie,refreshTokenInRedis) != true){
			log.info("JWT Authentication Filter : Refresh Token 불일치");
			throw new MalformedJwtException("Refresh Token이 유효하지 않습니다");
		}


		JwtToken newToken = jwtTokenProvider.refreshToken(refreshTokenInCookie);

		updateLoginInfo(loginInfo, newToken);

		return newToken;
	}

	private String getRefreshTokenFromCookie(HttpServletRequest request){

		return cookieManager
			.getCookie(request, OAuth2AuthorizationRequestRepository.REFRESH_TOKEN)
			.orElseThrow(() -> new NoSuchElementException("쿠키에 REFRESH 토큰이 존재하지 않습니다"))
			.getValue();
	}

	private String getUserUuid(String jwtToken) {

		Claims claims = jwtTokenProvider.parseClaims(jwtToken);

		return claims.getSubject();
	}

	private void updateLoginInfo(UserAuthDto userAuth, JwtToken newToken){

		log.info("JWT Authentication Filter : 기존 로그인 정보 업데이트");

		userAuthRepository.deleteById(userAuth.getUserUuid());

		UserAuth newLoginInfo = UserAuth.builder()
			.userUuid(userAuth.getUserUuid())
			.userKey(userAuth.getUserKey())
			.refreshToken(newToken.getRefreshToken())
			.build();

		userAuthRepository.save(newLoginInfo);
	}

}
