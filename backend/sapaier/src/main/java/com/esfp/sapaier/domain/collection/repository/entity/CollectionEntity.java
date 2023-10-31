package com.esfp.sapaier.domain.collection.repository.entity;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import com.esfp.sapaier.domain.user.repository.entity.User;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
// @NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Setter /////////////////////////////////////////////////////////////////
@Document(collection = "Collection")
public class CollectionEntity {

	@Id
	private String collectionId;
	private String path;
	private String name;
	private User user;

	private List<RequestEntity> apiList;
	private List<CollectionEntity> collectionList;

	@CreatedDate
	private LocalDateTime createdTime;
	@LastModifiedDate
	private LocalDateTime modifiedTime;

	/////////////////////////////////////////////////////////////////
	@Builder
	public CollectionEntity() {

	}
	@Builder
	public CollectionEntity(List<RequestEntity> list) {
		this.apiList = list;
	}
	/////////////////////////////////////////////////////////////////

	@Builder
	public CollectionEntity(String path) {
		this.path = path;
	}

	@Builder
	public CollectionEntity(String path, String name) {   //, User user){
		this.path = path;
		//        this.user = user;
		this.name = name;
	}

	public void update(String name) {
		this.name = name;
	}

	public void updatePath(String path) {
		this.path = path;
	}

	@Override
	public String toString() {
		return "CollectionEntity{" +
			"collectionId='" + collectionId + '\'' +
			", path='" + path + '\'' +
			", name='" + name + '\'' +
			", user=" + user +
			", apiList=" + apiList +
			", collectionList=" + collectionList +
			", createdTime=" + createdTime +
			", modifiedTime=" + modifiedTime +
			'}';
	}
}
