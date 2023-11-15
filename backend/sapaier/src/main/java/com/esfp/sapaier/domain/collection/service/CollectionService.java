package com.esfp.sapaier.domain.collection.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.esfp.sapaier.domain.collection.exception.NoCollectionException;
import com.esfp.sapaier.domain.collection.model.dto.request.CollectionListRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.ModifyCollectionRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.RequestRequestDTO;
import com.esfp.sapaier.domain.collection.model.dto.response.CollectionResponseDto;
import com.esfp.sapaier.domain.collection.model.dto.response.RequestResponseDTO;
import com.esfp.sapaier.domain.collection.repository.CollectionRepository;
import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CollectionService {

	private final CollectionRepository collectionRepository;
	private static final String NO_COLLECTION_EXCEPTION = "해당하는 collection이 존재하지 않습니다.";

	@Transactional
	public void createCollectionDocument() {
		String collectionName = "new Document";
		List<CollectionEntity> collectionList = new ArrayList<>();

		collectionRepository.save(new CollectionEntity(collectionName, collectionList));
	}

	public List<CollectionResponseDto> allCollectionList(CollectionListRequestDto collectionListRequestDto) {
		List<String> topCollectionIdList = collectionListRequestDto.getCollectionId();
		List<CollectionResponseDto> responseList = new ArrayList<CollectionResponseDto>();

		for (String collectionId : topCollectionIdList) {
			CollectionEntity collection = collectionRepository.findById(collectionId)
				.orElseThrow(() -> new NoCollectionException(NO_COLLECTION_EXCEPTION));
			responseList.add(new CollectionResponseDto(collection));
		}
		return responseList;
	}

	@Transactional
	public void modifyCollection(List<ModifyCollectionRequestDto> modifyCollectionRequestDto, String nowIndex) {
		CollectionEntity collection = collectionRepository.findById(modifyCollectionRequestDto.get(0).getCollectionId())
			.orElseThrow(() -> new NoCollectionException(NO_COLLECTION_EXCEPTION));
			Integer num = Integer.parseInt(nowIndex);
		collectionRepository.save(modifyCollectionRequestDto.get(num).modifyToEntity(
			modifyCollectionRequestDto.get(num).getCollectionId(),
			modifyCollectionRequestDto.get(num).getCollectionName(),
			modifyCollectionRequestDto.get(num).getApiList(),
			modifyCollectionRequestDto.get(num).getCollectionList(),
				modifyCollectionRequestDto.get(num).getCollapsed()
		));
	}

	public String rootCollectionName(String collectionId) {
		CollectionEntity collection = collectionRepository.findById(collectionId)
			.orElseThrow(() -> new NoCollectionException(NO_COLLECTION_EXCEPTION));
		return collection.getCollectionName();
	}

	public String lastCreateDocument() {
		CollectionEntity collection = collectionRepository.findTop1ByOrderByCreatedTimeDesc()
			.orElseThrow(() -> new NoCollectionException(NO_COLLECTION_EXCEPTION));
		return collection.getCollectionId();
	}

	public RequestResponseDTO sendRequest(RequestRequestDTO requestRequestDTO) {
		// 헤더 준비
		HttpHeaders httpHeaders = new HttpHeaders();    // 헤더 객체
		Map<String, String> headers = requestRequestDTO.getHeaders(); // 전달 받은 헤더 맵

		for (String key : headers.keySet()) {
			httpHeaders.set(key, headers.get(key));
		}

		// Request 전송
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5000); // 5 seconds
		factory.setReadTimeout(5000); // 5 seconds
		RestTemplate restTemplate = new RestTemplate(factory); // SpringBoot의 RestTemplate : HTTP + RestFUL API

		RequestResponseDTO requestResponseDTO = new RequestResponseDTO();

		long startTime = System.currentTimeMillis(), endTime;
		try {
			ResponseEntity<String> response = restTemplate.exchange( // API 요청 결과를 바로 반환
				requestRequestDTO.getRequestURL(),
				HttpMethod.valueOf(requestRequestDTO.getMethod().name()),
				new HttpEntity<>(requestRequestDTO.getBody(), httpHeaders),
				String.class
			);
			HttpStatus statusCode = (HttpStatus)response.getStatusCode();
			HttpHeaders responseHeaders = response.getHeaders();
			String responseBody = response.getBody();

			requestResponseDTO.setResponseHeaders(responseHeaders.toSingleValueMap());
			requestResponseDTO.setStatusCode(statusCode.value());
			requestResponseDTO.setStatusText(statusCode.toString());
			requestResponseDTO.setResponseBody(responseBody);

		} catch (HttpClientErrorException | HttpServerErrorException e) {

			requestResponseDTO.setStatusCode(e.getStatusCode().value());
			requestResponseDTO.setStatusText(e.getStatusText());
			requestResponseDTO.setResponseHeaders(e.getResponseHeaders().toSingleValueMap());
			requestResponseDTO.setResponseBody(e.getResponseBodyAsString());
			requestResponseDTO.setErrorMsg(e.getMessage());
			requestResponseDTO.setErrorStackTrace(e.getStackTrace());

		} catch (Exception e) {

			requestResponseDTO.setStatusCode(777);
			requestResponseDTO.setStatusText("정의되지 않은 에러 입니다.");
			requestResponseDTO.setErrorMsg(e.getMessage());
			requestResponseDTO.setErrorStackTrace(e.getStackTrace());

		} finally {
			endTime = System.currentTimeMillis();
		}

		requestResponseDTO.setResponseTime(endTime - startTime);

		return requestResponseDTO;
	}
}
