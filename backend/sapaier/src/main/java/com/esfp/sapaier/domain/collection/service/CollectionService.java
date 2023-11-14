package com.esfp.sapaier.domain.collection.service;

import com.esfp.sapaier.domain.collection.exception.NoCollectionException;
import com.esfp.sapaier.domain.collection.model.dto.request.CollectionListRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.request.ModifyCollectionRequestDto;
import com.esfp.sapaier.domain.collection.model.dto.response.CollectionResponseDto;
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
			modifyCollectionRequestDto.get(num).getCollectionList()
		));
	}

	public String rootCollectionName(String collectionId){
		CollectionEntity collection = collectionRepository.findById(collectionId)
				.orElseThrow(() -> new NoCollectionException(NO_COLLECTION_EXCEPTION));
		return collection.getCollectionName();
	}

	public String lastCreateDocument(){
		CollectionEntity collection = collectionRepository.findTop1ByOrderByCreatedTimeDesc()
				.orElseThrow(()-> new NoCollectionException(NO_COLLECTION_EXCEPTION));
		return collection.getCollectionId();
	}
}
