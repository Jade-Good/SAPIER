package com.esfp.sapaier.domain.collection.service;

import com.esfp.sapaier.domain.collection.exception.NoCollectionException;
import com.esfp.sapaier.domain.collection.repository.CollectionRepository;
import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import com.esfp.sapaier.domain.collection.service.dto.request.CollectionListRequestDto;
import com.esfp.sapaier.domain.collection.service.dto.request.CreateCollectionRequestDto;
import com.esfp.sapaier.domain.collection.service.dto.request.ModifyCollectionRequestDto;
import com.esfp.sapaier.domain.collection.service.dto.response.CollectionResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    private static final String NO_COLLECTION_EXCEPTION = "해당하는 collection이 존재하지 않습니다.";

	@Transactional
	public void createCollectionDocument(CreateCollectionRequestDto createCollectionRequestDto) {
		String collectionName = createCollectionRequestDto.getCollectionName();
        if(Objects.equals(collectionName, " ") || collectionName == null || collectionName.equals("")){
			collectionName = "New Collection";
        }
		List<CollectionEntity> collectionList = new ArrayList<>();

		collectionRepository.save(new CollectionEntity(collectionName,collectionList));
	}

	public List<CollectionResponseDto> allCollectionList(CollectionListRequestDto collectionListRequestDto){
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
	public void modifyCollection(ModifyCollectionRequestDto modifyCollectionRequestDto) {
		CollectionEntity collection = collectionRepository.findById(modifyCollectionRequestDto.getCollectionId())
				.orElseThrow(() -> new NoCollectionException(NO_COLLECTION_EXCEPTION));

		collectionRepository.save(modifyCollectionRequestDto.modifyToEntity(
				modifyCollectionRequestDto.getCollectionId(),
				modifyCollectionRequestDto.getCollectionName(),
				modifyCollectionRequestDto.getApiList(),
				modifyCollectionRequestDto.getCollectionList()
		));
	}
}
