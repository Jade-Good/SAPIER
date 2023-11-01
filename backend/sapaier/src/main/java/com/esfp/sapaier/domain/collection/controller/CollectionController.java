package com.esfp.sapaier.domain.collection.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.esfp.sapaier.domain.collection.model.dto.request.CollectionListRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.CreateCollectionRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.ModifyCollectionRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.RequestRequestDTO;
import com.esfp.sapaier.domain.collection.model.dto.response.CollectionResponseDto;
import com.esfp.sapaier.domain.collection.service.CollectionService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/v1/collection")
@RestController
public class CollectionController {

	private final CollectionService collectionService;

	@PostMapping
	public ResponseEntity<Object> registCollectionDocument(
		@RequestBody CreateCollectionRequestDto createCollectionRequestDto) {
		collectionService.createCollectionDocument(createCollectionRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@GetMapping
	public ResponseEntity<List<CollectionResponseDto>> collectionList(
		@RequestBody CollectionListRequestDto collectionListRequestDto) {
		return new ResponseEntity<>(collectionService.allCollectionList(collectionListRequestDto), HttpStatus.OK);
	}

	@PatchMapping
	public ResponseEntity<Object> modifyCollection(@RequestBody ModifyCollectionRequestDto modifyCollectionRequestDto) {
		collectionService.modifyCollection(modifyCollectionRequestDto);
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@PostMapping("/request")
	public ResponseEntity<String> sendRequest(@RequestBody RequestRequestDTO requestRequestDTO) {
		RestTemplate restTemplate = new RestTemplate(); // SpringBoot의 RestTemplate : HTTP + RestFUL API
		HttpHeaders httpHeaders = new HttpHeaders();    // 헤더 객체
		Map<String, String> map = requestRequestDTO.getHeaders(); // 전달 받은 헤더 맵

		for (String key : map.keySet()) { // 헤더 객체에 전달 받은 헤더 맵 넣어주기
			httpHeaders.set(key, map.get(key));
		}

		return restTemplate.exchange( // API 요청 결과를 바로 반환
			requestRequestDTO.getRequestURL(),
			HttpMethod.valueOf(requestRequestDTO.getMethod().name()),
			new HttpEntity<>(requestRequestDTO.getBody(), httpHeaders),
			String.class
		);
	}
}
