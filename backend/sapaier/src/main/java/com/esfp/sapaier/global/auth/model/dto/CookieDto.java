package com.esfp.sapaier.global.auth.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class CookieDto {
	private String name;
	private String value;
	private int maxAge;
}
