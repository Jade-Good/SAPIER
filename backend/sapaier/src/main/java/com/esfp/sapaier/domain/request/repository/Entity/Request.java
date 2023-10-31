package com.esfp.sapaier.domain.request.repository.Entity;

import java.util.HashMap;
import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Request")
public class Request {
	@MongoId
	private String id;
	private String requestName;
	private String requestURL;
	private String method;
	private HashMap<String, String> headers;
	private HashMap<String, String> queryParams;
	private HashMap<String, String> body;
	private HashMap<String, String> formData;
	private Date createdTime;
	private Date modifiedTime;
}
