package com.esfp.sapaier.domain.history.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestDto {
    private String requestName;
    private String requestURL;
    private String method;
    private HashMap<String, String>[] headers;
    private HashMap<String, String>[] queryParams;
    private String body;
}
