package com.esfp.sapaier.domain.history.model.dto;

import com.esfp.sapaier.domain.history.repository.entity.HistoryEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WorkspaceHistoryDto {
    private String uuid;
    private String workspaceKey;
    private LocalDate date;
    private List<HistoryEntity> historyList;

}
