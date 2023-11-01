package com.esfp.sapaier.domain.collection.model.dto.request;

import java.util.Map;

import com.esfp.sapaier.domain.collection.model.vo.Method;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class RequestRequestDTO {
	private String requestURL;
	private Method method;
	private Map<String, String> headers;
	private Map<String, String> queryParams;
	private Map<String, String> body;
	private Map<String, String> formData;

	@Override
	public String toString() {
		return "RequestRequestDTO{" +
			"requestURL='" + requestURL + '\'' +
			", method=" + method +
			", headers=" + headers +
			", queryParams=" + queryParams +
			", body=" + body +
			", formData=" + formData +
			'}';
	}
}
