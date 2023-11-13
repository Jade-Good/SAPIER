package com.esfp.sapaier.domain.collection.repository.entity;

import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Request")
public class RequestEntity {
	@MongoId
	private String id;
	private String requestName;
	private String requestURL;
	private String method;
	private HashMap<String, String>[] headers;
	private HashMap<String, String>[] queryParams;
	private String body;
	private Date createdTime;
	private Date modifiedTime;

	@Override
	public String toString() {
		return "RequestEntity{" +
			"id='" + id + '\'' +
			", requestName='" + requestName + '\'' +
			", requestURL='" + requestURL + '\'' +
			", method='" + method + '\'' +
			", headers=" + Arrays.toString(headers) +
			", queryParams=" + Arrays.toString(queryParams) +
			", body=" + body +
			", createdTime=" + createdTime +
			", modifiedTime=" + modifiedTime +
			'}';
	}
}
