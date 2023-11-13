package com.esfp.sapaier.domain.history.repository.entity;

import com.esfp.sapaier.domain.collection.repository.entity.RequestEntity;
import com.esfp.sapaier.domain.history.model.dto.ResponseDto;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.LocalDateTime;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Document(collection = "History")
public class HistoryEntity {
    @MongoId
    private String historyId;

    private RequestEntity request;
    private ResponseDto response;

    @CreatedDate
    private LocalDateTime createdTime;
//    @LastModifiedDate
//    private LocalDateTime modifiedTime;

    private String workSpaceId; //해당 request가 어느 workspace에 속했는지
    private String uuid;

}
