package com.esfp.sapaier.domain.request.repository.Entity;

import java.util.List;
import java.util.TreeSet;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Document(collection = "Collection")
public class Collection {
	@MongoId
	private String id;
	private TreeSet<Request> apiList;
}
