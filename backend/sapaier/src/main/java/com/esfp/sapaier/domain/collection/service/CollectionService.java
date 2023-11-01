package com.esfp.sapaier.domain.collection.service;

import com.esfp.sapaier.domain.collection.exception.NoCollectionException;
import com.esfp.sapaier.domain.collection.repository.CollectionRepository;
import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import com.esfp.sapaier.domain.collection.service.dto.request.CreateCollectionRequest;
import com.esfp.sapaier.domain.collection.service.dto.request.ModifyCollectionRequest;
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
    // TODO: 유저가 아니라 워크스페이스

    private static final String NO_COLLECTION_EXCEPTION = "해당하는 collection이 존재하지 않습니다.";


	public void test() {
		// RequestEntity request1 = new RequestEntity();
		// request1.setRequestName("요청1");
		// RequestEntity request2 = new RequestEntity();
		// request2.setRequestName("요청2");
		// RequestEntity request3 = new RequestEntity();
		// request3.setRequestName("요청3");
		//
		// List<RequestEntity> list = new ArrayList<>();
		// list.add(request1);
		// list.add(request2);
		// list.add(request3);
		//
		// CollectionEntity collection = new CollectionEntity(list);
		// CollectionEntity collection1 = new CollectionEntity();
		// CollectionEntity collection2 = new CollectionEntity();
		//
		// List<CollectionEntity> list2 = new ArrayList<>();
		// list2.add(collection1);
		// list2.add(collection2);
		//
		// collection.setCollectionList(list2);
		//
		// CollectionEntity collection3 = new CollectionEntity();
		// List<CollectionEntity> list3 = new ArrayList<>();
		// // list3.add(collection3);
		// collection2.setCollectionList(list3);
		//
		// // collection.setApiList(list);
		//
		// collectionRepository.save(collection);

		CollectionEntity entity = collectionRepository.findById("6540b56f3b518a72678f74c6").get();

		System.out.println(entity);
		System.out.println(entity.getApiList().get(0));
		System.out.println(entity.getCollectionList().get(1));
	}

}
