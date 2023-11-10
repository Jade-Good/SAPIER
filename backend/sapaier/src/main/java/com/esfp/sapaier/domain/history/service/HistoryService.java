package com.esfp.sapaier.domain.history.service;

import com.esfp.sapaier.domain.collection.repository.entity.RequestEntity;
import com.esfp.sapaier.domain.history.model.dto.DailyHistoryDto;
import com.esfp.sapaier.domain.history.model.dto.WorkspaceHistoryDto;
import com.esfp.sapaier.domain.history.repository.HistoryRepository;
import com.esfp.sapaier.domain.history.repository.entity.HistoryEntity;
import lombok.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public List<DailyHistoryDto> getAllDailyHistoryList(String uuid){
        List<HistoryEntity> historyList = historyRepository.findAllByUuidOrderByCreatedTimeDesc(uuid);
        List<List<HistoryEntity>> workspaceHistoryList = new LinkedList<>(); //같은 날짜의 history들 workspace별로 저장(날짜별로 계속 갱신)
        List<DailyHistoryDto> result = new LinkedList<>();
        LocalDate date = LocalDate.MIN;
        for(HistoryEntity history: historyList){
            LocalDate currentDate = history.getCreatedTime().toLocalDate();
            String currentWorkspaceId = history.getWorkSpaceId();
            if(workspaceHistoryList.size() == 0){ //최초 생성 시
                List<HistoryEntity> currentHistoryList = new LinkedList<>();
                currentHistoryList.add(history);
                workspaceHistoryList.add(currentHistoryList);
                date = currentDate;
            } else{
                //일단은 일자별로, 이후 workspace별로 저장
                if(!date.equals(currentDate)){ //만약 현재 history의 currentDate와 이전에 돌던 date가 다르다면(해당 날짜 끝났다면)
                    //해당 Date의 workspaceLists를 전부 DailyHistoryDto에 저장
                    List<WorkspaceHistoryDto> workspaceLists = new LinkedList<>();
                    for(List<HistoryEntity> workspace: workspaceHistoryList){
                        String workspaceId = workspace.get(0).getWorkSpaceId();
                        WorkspaceHistoryDto workspaceHistoryDto = new WorkspaceHistoryDto(uuid, workspaceId, date, workspace);
                        workspaceLists.add(workspaceHistoryDto);
                    }
                    DailyHistoryDto dailyHistoryDto = new DailyHistoryDto(uuid, date, workspaceLists);
                    result.add(dailyHistoryDto);
                    //이전 일자의 history 전부 저장했다면, workspaceHistoryList 초기화 후 새 history 저장 시작
                    workspaceHistoryList = new LinkedList<>();
                    List<HistoryEntity> currentHistoryList = new LinkedList<>();
                    currentHistoryList.add(history);
                    workspaceHistoryList.add(currentHistoryList);
                    date = currentDate;
                } else { //아직 해당 날짜 끝나지 않은 경우
                    boolean isExist = false;
                    for (List<HistoryEntity> curHistList : workspaceHistoryList) {
                        String targetWorkspaceId = curHistList.get(0).getWorkSpaceId();
                        if (currentWorkspaceId.equals(targetWorkspaceId)) {
                            //TODO: 일치하는 workspaceList 찾았다면 거기에 넣어줌
                            curHistList.add(history);
                            isExist = true;
                            break;
                        } else continue;
                    }
                    if (!isExist) { //workspaceHistoryList에서 현재 히스토리가 속한 workspace를 찾지 못했다면,
                        //새로운 List 추가해줌
                        List<HistoryEntity> newHistList = new LinkedList<>();
                        newHistList.add(history);
                        workspaceHistoryList.add(newHistList);
                    }
                }
            }
        }
        //TODO: 마지막 DailyHistoryDto도 result에 add 시켜줘야 함
        List<WorkspaceHistoryDto> workspaceLists = new LinkedList<>();
        for(List<HistoryEntity> workspace: workspaceHistoryList){
            String workspaceId = workspace.get(0).getWorkSpaceId();
            WorkspaceHistoryDto workspaceHistoryDto = new WorkspaceHistoryDto(uuid, workspaceId, date, workspace);
            workspaceLists.add(workspaceHistoryDto);
        }
        DailyHistoryDto dailyHistoryDto = new DailyHistoryDto(uuid, date, workspaceLists);
        result.add(dailyHistoryDto);

        return result;
    }
    public void saveHistory(RequestEntity request, String workSpaceId, String uuid){
        HistoryEntity history = HistoryEntity.builder()
                .request(request)
                .workSpaceId(workSpaceId)
                .uuid(uuid)
                .createdTime(LocalDateTime.now())
                .build();
        historyRepository.save(history);
    }

}
