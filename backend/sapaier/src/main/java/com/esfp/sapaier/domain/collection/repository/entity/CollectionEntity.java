package com.esfp.sapaier.domain.collection.repository.entity;

import com.esfp.sapaier.domain.user.repository.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Document(collection = "Collection")
public class CollectionEntity {

    @Id
    private String collectionId;
    private String path;
    private String name;
    private User user;
   // private List<리퀘스트엔티티들어갈예정> apiList;
    @CreatedDate
    private LocalDateTime createdTime;
    @LastModifiedDate
    private LocalDateTime modifiedTime;


    @Builder
    public CollectionEntity (String path){
        this.path = path;
    }

    @Builder
    public CollectionEntity(String path, String name ) {   //, User user){
        this.path = path;
//        this.user = user;
        this.name = name;
    }

    public void update(String name){
        this.name = name;
    }

    public void updatePath(String path){
        this.path = path;
    }

}
