package com.esfp.sapaier.domain.history.controller;

import com.esfp.sapaier.domain.history.model.dto.DailyHistoryDto;
import com.esfp.sapaier.domain.history.service.HistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/v1/history")
@RestController
public class HistoryController {
    private final HistoryService historyService;

    @GetMapping
    public ResponseEntity<List<DailyHistoryDto>> getHistoryList(@RequestParam String uuid){
        List<DailyHistoryDto> historyList = historyService.getAllDailyHistoryList(uuid);
        return new ResponseEntity<>(historyList, HttpStatus.OK);
    }
}
