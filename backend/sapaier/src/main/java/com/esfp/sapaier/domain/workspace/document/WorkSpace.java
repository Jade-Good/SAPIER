package com.esfp.sapaier.domain.workspace.document;


import com.esfp.sapaier.domain.workspace.dto.CollectionListDto;
import com.esfp.sapaier.domain.workspace.dto.UserPermissionDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.context.properties.bind.DefaultValue;
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

    private String color="#0F4C81";
    @NotNull
    private List<CollectionListDto> collectionList;
    @NotNull
    private List<UserPermissionDto> memberList;
    private List<String> collectionId;
    @NotNull
    @CreatedDate
    private LocalDateTime createTime;
    @NotNull
    @LastModifiedDate
    private LocalDateTime modifiedTime;
//    private Date modifiedTime;


}
