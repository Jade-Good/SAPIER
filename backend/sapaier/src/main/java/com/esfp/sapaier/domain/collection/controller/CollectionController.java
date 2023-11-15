package com.esfp.sapaier.domain.collection.controller;

import com.esfp.sapaier.domain.collection.model.dto.request.CollectionListRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.ModifyCollectionRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.RequestRequestDTO;
import com.esfp.sapaier.domain.collection.model.dto.response.CollectionResponseDto;
import com.esfp.sapaier.domain.collection.service.CollectionService;
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
	public ResponseEntity<Object> registCollectionDocument() {
		collectionService.createCollectionDocument();
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/list")
	public ResponseEntity<List<CollectionResponseDto>> collectionList(
		@RequestBody CollectionListRequestDto collectionListRequestDto) {
		return new ResponseEntity<>(collectionService.allCollectionList(collectionListRequestDto), HttpStatus.OK);
	}

	@GetMapping("/{collectionId}")
	public ResponseEntity<String> rootCollectionName(@PathVariable String collectionId) {
		return new ResponseEntity<>(collectionService.rootCollectionName(collectionId), HttpStatus.OK);
	}


	@PatchMapping("/modify/{nowIndex}")
	public ResponseEntity<Object> modifyCollection(@RequestBody List<ModifyCollectionRequestDto> modifyCollectionRequestDto, @PathVariable String nowIndex) {
		collectionService.modifyCollection(modifyCollectionRequestDto,nowIndex);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping("/last")
	public ResponseEntity<String> lastCreateDocument(){
		return new ResponseEntity<>(collectionService.lastCreateDocument(),HttpStatus.OK);
	}

	@PostMapping("/request")
	public ResponseEntity<Object> sendRequest(@RequestBody RequestRequestDTO requestRequestDTO) {
		return new ResponseEntity<>(collectionService.sendRequest(requestRequestDTO), HttpStatus.OK);
	}
}
