package com.esfp.sapaier.domain.history.model.dto;

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
public class DailyHistoryDto {
    private String uuid;
    private LocalDate date;
    private List<WorkspaceHistoryDto> workspaceHistories;

}
