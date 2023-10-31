package com.esfp.sapaier.domain.collection.service.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ModifyFolderRequest {
    private String collectionId;
    private String name;
}
