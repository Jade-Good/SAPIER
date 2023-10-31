package com.esfp.sapaier.domain.workspace.repository;

import com.esfp.sapaier.domain.workspace.document.WorkSpace;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkSpaceRepository extends MongoRepository<WorkSpace, String>, CustomWorkSpaceRepository{

//    @DeleteQuery(value = "{'_id': '1', 'memberList': { $elemMatch: { 'uuId': '1' } } }")
//    void deleteMemberWithUuId(String workspaceidx);


//    @Query("{'_id': ?0, 'memberList.uuId': ?1}")
//    void deleteByWorkspaceIdxAndUuId(@Param("_id") String workspaceidx, @Param("uuId") String uuId);

    // 'uuId'가 1인 항목만 삭제하는 메서드
//    @Query("{'_id': ?0, 'memberList': { $elemMatch: { 'uuId': '1' } } }")
//    void deleteByUuIdOne(@Param("_id")String workspaceidx);

}
