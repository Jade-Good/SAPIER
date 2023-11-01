package com.esfp.sapaier.global.auth.util;

import com.esfp.sapaier.global.auth.model.vo.JwtToken;

import java.security.Key;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {


	public static final long ACCESS_TOKEN_EXPIRE_TIME = 1000  * 60 * 5L;
	public static final long REFRESH_TOKEN_EXPIRE_TIME = 1000 * 60 * 60 * 24 * 7L;

	public static final int ACCESS_TOKEN_EXPIRE_TIME_COOKIE = 30 * 60;
	public static final int REFRESH_TOKEN_EXPIRE_TIME_COOKIE = 365 * 24 * 60 * 60;


	private final String BEARER_TYPE = "Bearer";
	private final Key key;

	public JwtTokenProvider(
		@Value("${jwt.token.key}") String secret) {

		byte[] keyBytes = Decoders.BASE64.decode(secret);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}

	public JwtToken createToken(String uuid, String role) {

		Long curTime = new Date().getTime();

		String accessToken = Jwts
			.builder()
			.setSubject(uuid)
			.setIssuedAt(new Date())
			.claim("ROLE", role)
			.signWith(key, SignatureAlgorithm.HS256)
			.setExpiration(new Date(curTime + ACCESS_TOKEN_EXPIRE_TIME))
			.compact();

		String refreshToken = Jwts
			.builder()
			.setSubject(uuid)
			.setIssuedAt(new Date())
			.claim("ROLE", role)
			.signWith(key, SignatureAlgorithm.HS256)
			.setExpiration(new Date(curTime + REFRESH_TOKEN_EXPIRE_TIME))
			.compact();

		return JwtToken
			.builder()
			.grantType(BEARER_TYPE)
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.expireTime(REFRESH_TOKEN_EXPIRE_TIME)
			.build();
	}

	public JwtToken refreshToken(String refreshToken){

		Claims claims = parseClaims(refreshToken);

		String uuid = claims.getSubject();
		String role = claims.get("ROLE").toString();

		return this.createToken(uuid,role);
	}

	public Authentication getAuthentication(String accessToken) {
		Claims claims = parseClaims(accessToken);

		if (claims.get("ROLE") == null)
			throw new InsufficientAuthenticationException("권한이 없습니다.");


		Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

		StringTokenizer st = new StringTokenizer(claims.get("ROLE").toString(),",");
		while(st.hasMoreTokens()){
			String role = st.nextToken();
			authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
		}

		UserDetails principal = new User(claims.getSubject(), "", authorities);

		return new UsernamePasswordAuthenticationToken(principal, accessToken, authorities);
	}

	public Claims parseClaims(String jwtToken) throws
		MalformedJwtException,
		ExpiredJwtException,
		UnsupportedJwtException,
		IllegalArgumentException,
		SignatureException{

		Claims claims =  Jwts
			.parserBuilder()
			.setSigningKey(key)
			.build()
			.parseClaimsJws(jwtToken)
			.getBody();

		return  claims;
	}
}
