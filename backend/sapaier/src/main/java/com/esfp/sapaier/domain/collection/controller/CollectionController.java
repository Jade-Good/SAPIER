package com.esfp.sapaier.domain.collection.controller;

import com.esfp.sapaier.domain.collection.service.CollectionService;
import com.esfp.sapaier.domain.collection.service.dto.request.CreateFolderRequest;
import com.esfp.sapaier.domain.collection.service.dto.request.DeleteFolderRequest;
import com.esfp.sapaier.domain.collection.service.dto.request.ModifyFolderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection")
@RestController
public class CollectionController {

    private final CollectionService collectionService;

    @PostMapping
    public ResponseEntity<Object> registCollection(Authentication authentication){
      //  log.info("현재 로그인한 사용자 정보로 뭐가 넘어오나? : {}",authentication.getName());
        collectionService.registCollection();
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/folder")
    public ResponseEntity<?> registFolder(@RequestBody CreateFolderRequest createFolderRequest, Authentication authentication) {
        collectionService.registFolder(createFolderRequest);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/folder")
    public ResponseEntity<?> deleteFolder(@RequestBody DeleteFolderRequest deleteFolderRequest, Authentication authentication){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/folder")
    public ResponseEntity<?> modifyFolder(@RequestBody ModifyFolderRequest modifyFolderRequest, Authentication authentication){

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/{collectionIdx}")
    public ResponseEntity<?> modifyCollection(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{collectionIdx}")
    public ResponseEntity<?> deleteCollection(){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/request")
    public ResponseEntity<?> test(){
        collectionService.test();
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
