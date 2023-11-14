package com.esfp.sapaier.domain.history.controller;

import com.esfp.sapaier.domain.collection.repository.entity.RequestEntity;
import com.esfp.sapaier.domain.history.model.dto.DailyHistoryDto;
import com.esfp.sapaier.domain.history.model.dto.RequestDto;
import com.esfp.sapaier.domain.history.model.dto.ResponseDto;
import com.esfp.sapaier.domain.history.model.dto.request.HistoryRequestDto;
import com.esfp.sapaier.domain.history.service.HistoryService;
import com.esfp.sapaier.global.auth.service.UserAuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
@RestController
public class HistoryController {
    private final HistoryService historyService;
    private final UserAuthService userAuthService;

    @GetMapping
    public ResponseEntity<List<DailyHistoryDto>> getHistoryList(
            @RequestHeader(name="Authorization", required=false) String bearerToken,
            @CookieValue(name="accessToken", required = false) String accessToken
    ){
        String uuid = userAuthService.getUserKeyFromUuid(accessToken, bearerToken);
        List<DailyHistoryDto> historyList = historyService.getAllDailyHistoryList(uuid);
        return new ResponseEntity<>(historyList, HttpStatus.OK);
    }

    @PostMapping("/save")
    public ResponseEntity<?> saveHistory(@RequestBody HistoryRequestDto historyRequestDto){
        RequestDto request = historyRequestDto.getRequest();
        ResponseDto response = historyRequestDto.getResponse();
        String workspaceId = historyRequestDto.getWorkspaceId();
        String uuid = historyRequestDto.getUuid();
        historyService.saveHistory(request, response, workspaceId, uuid);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
