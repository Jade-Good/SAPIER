package com.esfp.sapaier.domain.collection.controller;

import com.esfp.sapaier.domain.collection.model.dto.request.CollectionListRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.ModifyCollectionRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.RequestRequestDTO;
import com.esfp.sapaier.domain.collection.model.dto.response.CollectionResponseDto;
import com.esfp.sapaier.domain.collection.service.CollectionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

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
	public ResponseEntity<String> sendRequest(@RequestBody RequestRequestDTO requestRequestDTO) {
		RestTemplate restTemplate = new RestTemplate(); // SpringBoot의 RestTemplate : HTTP + RestFUL API
		HttpHeaders httpHeaders = new HttpHeaders();    // 헤더 객체
		Map<String, String>[] maps = requestRequestDTO.getHeaders(); // 전달 받은 헤더 맵

		for (Map<String, String> map : maps) {
				httpHeaders.set(map.get("key"), map.get("value"));
		}

		return restTemplate.exchange( // API 요청 결과를 바로 반환
			requestRequestDTO.getRequestURL(),
			HttpMethod.valueOf(requestRequestDTO.getMethod().name()),
			new HttpEntity<>(requestRequestDTO.getBody(), httpHeaders),
			String.class
		);
	}
}
