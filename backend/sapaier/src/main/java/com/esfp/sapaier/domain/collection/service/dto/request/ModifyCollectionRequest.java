package com.esfp.sapaier.domain.collection.service.dto.request;

import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import com.esfp.sapaier.domain.collection.repository.entity.RequestEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class ModifyCollectionRequest {
    private String collectionId;
    private String collectionName;
    private List<RequestEntity> apiList;
    private List<CollectionEntity> collectionList;

    public CollectionEntity modifyToEntity(String collectionId, String collectionName, List<RequestEntity> apiList, List<CollectionEntity> collectionList) {
        return CollectionEntity.builder()
                .collectionId(collectionId)
                .collectionName(collectionName)
                .apiList(apiList)
                .collectionList(collectionList)
                .modifiedTime(LocalDateTime.now())
                .build();
    }

}
