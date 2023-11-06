package com.esfp.sapaier.domain.workspace.service;


import com.esfp.sapaier.domain.workspace.document.WorkSpace;
import com.esfp.sapaier.domain.workspace.dto.AddMemberDto;
import com.esfp.sapaier.domain.workspace.dto.UserDataDto;
import com.esfp.sapaier.domain.workspace.dto.UserPermissionDto;
import com.esfp.sapaier.domain.workspace.exception.NoWorkspaceException;
import com.esfp.sapaier.domain.workspace.repository.UserDataRepository;
import com.esfp.sapaier.domain.workspace.repository.WorkSpaceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


@Service
@AllArgsConstructor
public class WorkSpaceService {
    private final WorkSpaceRepository workSpaceRepository;
    private final UserDataRepository userDataRepository;

    private final String NO_WORKSPACE_EXCEPTION = "해당하는 워크스페이스가 존재하지 않습니다."

    public List<WorkSpace> WorkSpaceList(String uuid) {
        return workSpaceRepository.findWorkSpaceList(uuid);
    }

    public void addWorkSpace(WorkSpace workSpace) {
        workSpaceRepository.save(workSpace);
    }

    public void updateWorkSpace(WorkSpace workSpace) {
        workSpaceRepository.save(workSpace);
    }

    public WorkSpace infoWorkSpace(String workSpaceIdx) {
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workSpaceIdx);

        if (optionalWorkSpace.isPresent()) {
            WorkSpace workSpace = optionalWorkSpace.get();
            return  workSpace;
        } else {
            return  null;
        }
    }
//    public WorkSpace searchWorkSpace(String workspaceIdx) {
//        WorkSpace workSpace = workSpaceRepository.findById(workspaceIdx).orElseThrow(() -> new NoSuchElementException("정보가 존재하지 않습니다"));
//        return workSpace;
//    }



    //test : member 1명씩 찾기
    public List<UserDataDto> searhMember(String workSpaceIdx) {
        WorkSpace workSpace = workSpaceRepository.findById(workSpaceIdx).orElseThrow(() -> new NoSuchElementException("정보가 존재하지 않습니다"));
        List<UserDataDto> MemberList = new ArrayList<>();

        for (int i = 0; i < workSpace.getMemberList().size(); i++) {
            MemberList.add(userDataRepository.searchMember(workSpace.getMemberList().get(i).getUuId()));
        }

        return MemberList;
    }

    public void addMember(String workspaceidx, String memberuuId, String memberPermission) {
//        workSpaceRepository.deleteUserPermissionByWorkspaceIdxAndUuId(workspaceidx,memberuuId);
//        workSpaceRepository.deleteByWorkspaceIdxAndUuId(workspaceidx, memberuuId);
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workspaceidx);

        if (optionalWorkSpace.isPresent()) {
            WorkSpace workSpace = optionalWorkSpace.get();
//            System.out.println(workSpace);
            List<UserPermissionDto> userList =workSpace.getMemberList();
            userList.add(new UserPermissionDto(memberuuId,memberPermission));
            workSpaceRepository.save(workSpace);
//            return  workSpace;

        }
//        else {
//
//            return  null;
//        }

    }




    public void deleteMember(String workspaceidx, String memberuuId) {
//        workSpaceRepository.deleteUserPermissionByWorkspaceIdxAndUuId(workspaceidx,memberuuId);
//        workSpaceRepository.deleteByWorkspaceIdxAndUuId(workspaceidx, memberuuId);
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workspaceidx);

        if (optionalWorkSpace.isPresent()) {
            WorkSpace workSpace = optionalWorkSpace.get();
//            System.out.println(workSpace);
            List<UserPermissionDto> userList =workSpace.getMemberList();
            for (int i = 0; i < userList.size(); i++) {
                if(userList.get(i).getUuId().equals(memberuuId)) {
                    userList.remove(i);
                }
            }
            workSpaceRepository.save(workSpace);
//            return  workSpace;

        }
//        else {
//
//            return  null;
//        }

    }


    public void changePermissionMember(AddMemberDto addMemberDto,String memberUUID) {
        Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(addMemberDto.getWorkspaceIdx());

        if (optionalWorkSpace.isPresent()) {
            WorkSpace workSpace = optionalWorkSpace.get();
//            System.out.println(workSpace);
            List<UserPermissionDto> userList =workSpace.getMemberList();
            for (int i = 0; i < userList.size(); i++) {
                if(userList.get(i).getUuId().equals(memberUUID)) {
                    userList.set(i,new UserPermissionDto(addMemberDto.getMemberUuid(), addMemberDto.getPermission()));
                }
            }
            workSpaceRepository.save(workSpace);
//            return  workSpace;

        }
    }

    public String searchWorkspaceName(String workspaceIdx){
        WorkSpace workSpace = workSpaceRepository.findById(workspaceIdx)
                .orElseThrow(()-> new NoWorkspaceException(NO_WORKSPACE_EXCEPTION));
        return workSpace.getName();
    }



}
