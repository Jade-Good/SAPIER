package com.esfp.sapaier.domain.collection.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CollectionListRequestDto {
    private List<String> collectionId;
}
