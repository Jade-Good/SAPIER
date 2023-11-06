package com.esfp.sapaier.domain.collection.model.dto.response;

import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import com.esfp.sapaier.domain.collection.repository.entity.RequestEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class CollectionResponseDto {

    private String collectionId;
    private String collectionName;
    private List<RequestEntity> apiList;
    private List<CollectionEntity> collectionList;
    private LocalDateTime modifiedTime;

    public CollectionResponseDto(CollectionEntity collection) {
        this.collectionId = collection.getCollectionId();
        this.collectionName = collection.getCollectionName();
        this.apiList = collection.getApiList();
        this.collectionList = collection.getCollectionList();
        this.modifiedTime = collection.getModifiedTime();
    }

}
