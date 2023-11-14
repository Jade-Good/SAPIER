package com.esfp.sapaier.domain.collection.model.dto.response;

import java.util.Map;

import com.esfp.sapaier.domain.collection.model.vo.Method;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class RequestResponseDTO {

	private int statusCode;
	private String statusText;
	private Method method;
	private Map<String, String> responseHeaders;
	private String responseBody;
	private Long responseTime;
	private String errorMsg;
	private StackTraceElement[] errorStackTrace;

	@Override
	public String toString() {
		return "RequestResponseDTO{" +
			"statusCode=" + statusCode +
			", statusText='" + statusText + '\'' +
			", method=" + method +
			", responseHeaders=" + responseHeaders +
			", responseBody='" + responseBody + '\'' +
			", responseTime=" + responseTime +
			", errorMsg='" + errorMsg + '\'' +
			", errorStackTrace='" + errorStackTrace + '\'' +
			'}';
	}
}
