package com.esfp.sapaier.domain.workspace.document;


import com.esfp.sapaier.domain.workspace.dto.CollectionListDto;
import com.esfp.sapaier.domain.workspace.dto.UserPermissionDto;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Document(collection = "WorkSpace")
@NoArgsConstructor
@Getter
@AllArgsConstructor
public class WorkSpace {

    @MongoId
    private String key;
    @NotNull
    private String name;

    private String color="#0F4C81";
    private List<CollectionListDto> pinnedList;
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
    @NotNull
    private String Admin;
    private String summaryData;
    private String documentData;

//    private Date modifiedTime;

    @Builder
    public WorkSpace(WorkSpace workSpace,List<CollectionListDto> collectionListDtoList) {
        this.key = workSpace.getKey();
        this.name = workSpace.getName();
        this.memberList = workSpace.getMemberList();
        this.modifiedTime = LocalDateTime.now();
        this.collectionList = collectionListDtoList;
    }

}
