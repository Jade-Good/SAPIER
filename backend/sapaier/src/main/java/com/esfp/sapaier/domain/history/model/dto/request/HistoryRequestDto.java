package com.esfp.sapaier.domain.history.model.dto.request;

import com.esfp.sapaier.domain.collection.repository.entity.RequestEntity;
import com.esfp.sapaier.domain.history.model.dto.ResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryRequestDto {
    private RequestEntity request;
    private ResponseDto response;
    private String workspaceId;
    private String uuid;
}
