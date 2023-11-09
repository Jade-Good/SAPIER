package com.esfp.sapaier.domain.history.model.dto.request;

import com.esfp.sapaier.domain.collection.repository.entity.RequestEntity;
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
    private String workspaceId;
    private String uuid;
}
