package com.esfp.sapaier.domain.workspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddMemberDto {
    String workspaceIdx;
    String memberUuid;
    String permission;
}
