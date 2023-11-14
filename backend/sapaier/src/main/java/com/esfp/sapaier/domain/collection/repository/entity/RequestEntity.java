package com.esfp.sapaier.domain.collection.repository.entity;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
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
	private String[] path;

	@CreatedDate
	private Instant createdTime;
	@LastModifiedDate
	private LocalDateTime modifiedTime;

	@Override
	public String toString() {
		return "RequestEntity{" +
			"id='" + id + '\'' +
			", requestName='" + requestName + '\'' +
			", requestURL='" + requestURL + '\'' +
			", method='" + method + '\'' +
			", headers=" + Arrays.toString(headers) +
			", queryParams=" + Arrays.toString(queryParams) +
			", body='" + body + '\'' +
			", path=" + Arrays.toString(path) +
			", createdTime=" + createdTime +
			", modifiedTime=" + modifiedTime +
			'}';
	}
}
