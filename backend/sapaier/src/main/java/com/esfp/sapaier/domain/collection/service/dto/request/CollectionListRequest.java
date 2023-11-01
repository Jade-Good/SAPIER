package com.esfp.sapaier.domain.collection.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CollectionListRequest {
    private List<String> collectionId;
}
