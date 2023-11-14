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
public class ResponseDto { //Response 정보를 저장해놓는 Dto
    private int statusCode; //상태코드
    private String statusText;
    private long responseTime; //응답시간
    private HashMap<String, String> header;
    private String body;
    private String errorMsg;
    private String errorStackTrace;
}
