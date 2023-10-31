package com.esfp.sapaier.domain.collection.service;

import com.esfp.sapaier.domain.collection.repository.CollectionRepository;
import com.esfp.sapaier.domain.collection.repository.entity.CollectionEntity;
import com.esfp.sapaier.domain.collection.service.dto.request.CreateFolderRequest;
import com.esfp.sapaier.domain.collection.service.dto.request.DeleteFolderRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class CollectionService {

    private final CollectionRepository collectionRepository;
    // TODO: user 정보도 넘겨줘야함

    private static final String NO_COLLECTION_EXCEPTION = "해당하는 collection이 존재하지 않습니다.";
    @Transactional
    public void registCollection() {
        collectionRepository.save(new CollectionEntity("/"));
    }

    // create blank folder
    @Transactional
    public void registFolder(CreateFolderRequest createFolderRequest) {

        // 폴더이름 없을 시 default값 넣어주기
        String folderName = createFolderRequest.getName();
        if(Objects.equals(folderName, " ") || folderName == null || folderName.equals("")){
            folderName = "New Folder";
        }
        String path = createFolderRequest.getPath()+"/"+folderName;
        collectionRepository.save(new CollectionEntity(path,folderName));

    }

    @Transactional
    public void deleteFolder(DeleteFolderRequest deleteFolderRequest) {

    }



}
