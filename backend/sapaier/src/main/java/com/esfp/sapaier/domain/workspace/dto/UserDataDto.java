package com.esfp.sapaier.domain.workspace.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Builder
@Document(collection = "User")
@AllArgsConstructor
public class UserDataDto {
    String uuid;
    String nickname;
    String email;
    String profileImageUrl;
}
