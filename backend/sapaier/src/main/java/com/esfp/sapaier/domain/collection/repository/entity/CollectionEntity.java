package com.esfp.sapaier.domain.collection.repository.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;


@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AllArgsConstructor
@Document(collection = "Collection")
public class CollectionEntity {

	@MongoId
	private String collectionId;
	private String collectionName;

	private List<RequestEntity> apiList;
	private List<CollectionEntity> collectionList;
	private Boolean collapsed;

	@CreatedDate
	private Instant createdTime;
	@LastModifiedDate
	private LocalDateTime modifiedTime;

	@Builder
	public CollectionEntity(String collectionName , List<CollectionEntity> collectionList) {
		this.collectionName = collectionName;
		this.collectionList = collectionList;
		this.createdTime = Instant.now();
		this.collapsed = false;
	}

	@Override
	public String toString() {
		return "CollectionEntity{" +
			"collectionId='" + collectionId + '\'' +
			", collectionName='" + collectionName + '\'' +
			", apiList=" + apiList +
			", collectionList=" + collectionList +
			", createdTime=" + createdTime +
			", modifiedTime=" + modifiedTime +
			'}';
	}
}
