package com.esfp.sapaier.global.auth.util;

import java.util.Base64;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.util.SerializationUtils;

import com.esfp.sapaier.global.auth.model.dto.CookieDto;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CookieManager {
	public Optional<Cookie> getCookie(HttpServletRequest request, String name) {

		Cookie[] cookies = request.getCookies();

		if(cookies == null)
			return Optional.empty();


		for (Cookie cookie : cookies) {
			if (name.equals(cookie.getName())) {
				return Optional.of(cookie);
			}
		}

		return Optional.empty();
	}

	public void addCookie(HttpServletResponse response, CookieDto cookieDto) {

		Cookie cookie = new Cookie(cookieDto.getName(), cookieDto.getValue());
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		cookie.setMaxAge(cookieDto.getMaxAge());

		response.addCookie(cookie);
	}

	public void deleteCookie(
		HttpServletRequest request,
		HttpServletResponse response,
		String targetName) {

		Cookie[] cookies = request.getCookies();

		if(cookies == null)
			return;

		for (Cookie cookie : cookies) {
			if (cookie.getName().equals(targetName)) {
				cookie.setValue("");
				cookie.setPath("/");
				cookie.setMaxAge(0);
				response.addCookie(cookie);
			}
		}

	}

	public void updateCookie(
		HttpServletRequest request,
		HttpServletResponse response,
		String targetName,
		CookieDto newCookieDto){

		deleteCookie(request,response,targetName);
		addCookie(response,newCookieDto);

	}


	public String serialize(Object obj) {
		return Base64.getUrlEncoder().encodeToString(SerializationUtils.serialize(obj));
	}

	public <T> T deserialize(Cookie cookie, Class<T> cls) {
		return cls.cast(SerializationUtils.deserialize(Base64.getUrlDecoder().decode(cookie.getValue())));
	}
}
