package com.esfp.sapaier.domain.workspace.document;


import com.esfp.sapaier.domain.workspace.dto.CollectionListDto;
import com.esfp.sapaier.domain.workspace.dto.UserPermissionDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "WorkSpace")
@AllArgsConstructor
public class WorkSpace {

    @MongoId
    private String key;
    @NotNull
    private String name;
    @NotNull
    private List<CollectionListDto> collectionList;
    @NotNull
    private List<UserPermissionDto> memberList;
    @NotNull
    @CreatedDate
    private LocalDateTime createTime;
    @NotNull
    @LastModifiedDate
    private LocalDateTime modifiedTime;
//    private Date modifiedTime;


}
