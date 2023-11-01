package com.esfp.sapaier.domain.collection.controller;

import com.esfp.sapaier.domain.collection.service.CollectionService;
import com.esfp.sapaier.domain.collection.model.dto.request.CollectionListRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.CreateCollectionRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.ModifyCollectionRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.response.CollectionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection")
@RestController
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<Object> registCollectionDocument(@RequestBody CreateCollectionRequestDto createCollectionRequestDto){
        collectionService.createCollectionDocument(createCollectionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<CollectionResponseDto>> collectionList(@RequestBody CollectionListRequestDto collectionListRequestDto) {
        return new ResponseEntity<>(collectionService.allCollectionList(collectionListRequestDto),HttpStatus.OK);
    }

   @PatchMapping
    public ResponseEntity<Object> modifyCollection(@RequestBody ModifyCollectionRequestDto modifyCollectionRequestDto){
        collectionService.modifyCollection(modifyCollectionRequestDto);
        return new ResponseEntity<>(HttpStatus.OK);
   }
}
