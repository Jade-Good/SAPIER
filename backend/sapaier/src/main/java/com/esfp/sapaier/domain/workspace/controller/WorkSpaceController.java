package com.esfp.sapaier.domain.workspace.controller;


import com.esfp.sapaier.domain.collection.model.dto.request.ModifyCollectionRequestDto;
import com.esfp.sapaier.domain.workspace.document.WorkSpace;
import com.esfp.sapaier.domain.workspace.dto.EmailRequest;
import com.esfp.sapaier.domain.workspace.dto.UserDataDto;
import com.esfp.sapaier.domain.workspace.dto.AddMemberDto;
import com.esfp.sapaier.domain.workspace.dto.UserPermissionDto;
import com.esfp.sapaier.domain.workspace.service.WorkSpaceService;
import com.esfp.sapaier.global.auth.util.JwtTokenProvider;
import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class WorkSpaceController {
    private final WorkSpaceService workSpaceService;
    private final JwtTokenProvider jwtTokenProvider;


    //workspace 목록 조회
    @GetMapping("api/v1/workspaces")
    public List<WorkSpace> WorkSpaceList(@CookieValue String accessToken){//@RequestBody String uuId
        String userUuid = jwtTokenProvider.parseClaims(accessToken).getSubject();
        return workSpaceService.WorkSpaceList(userUuid);
    }


    //워크스페이스 생성
    @PostMapping("api/v1/workspaces")
    public void addWorkSpace(@RequestBody WorkSpace workSpace, @CookieValue String accessToken){
        String userUuid = jwtTokenProvider.parseClaims(accessToken).getSubject();
        List<UserPermissionDto> list = new ArrayList<>();
        UserPermissionDto userPermissionDto = new UserPermissionDto();
        userPermissionDto.setUserPermission("admin");
        userPermissionDto.setUuId(userUuid);
        list.add(userPermissionDto);
        workSpace.setMemberList(list);
        workSpaceService.addWorkSpace(workSpace);
    }

    //워크스페이스 정보 조회
    @GetMapping("api/v1/workspaces/{workspaceIdx}")
    public WorkSpace infoWorkSpace(@PathVariable String workspaceIdx){
        return workSpaceService.infoWorkSpace(workspaceIdx);
    }
    @GetMapping("/api/v1/workspaces/{workspaceIdx}/name")
    public ResponseEntity<String> searchWorkspaceName(@PathVariable String workspaceIdx) {
        return new ResponseEntity<>(workSpaceService.searchWorkspaceName(workspaceIdx),HttpStatus.OK);
    }

    //워크스페이스 수정
    @PatchMapping("api/v1/workspaces/{workspaceIdx}")
    public void updateWorkSpace(@RequestBody WorkSpace workSpace, @PathVariable String workspaceIdx){
        workSpaceService.updateWorkSpace(workSpace);
    }

    //워크스페이스 삭제
    @DeleteMapping("api/v1/workspaces/{workspaceIdx}")
    public void deleteMember(@PathVariable String workspaceIdx){
        workSpaceService.deleteWorkSpace(workspaceIdx);
    }

    //워크스페이스 팀원 목록 조회
    @GetMapping("api/v1/workspaces/members/{workspaceIdx}")
    public List<UserDataDto> searchMember(@PathVariable String workspaceIdx){

        return workSpaceService.searhMember(workspaceIdx);
//         return testRepository.findAll();
    }


    //워크스페이스 팀원 추가
    @PostMapping("api/v1/workspaces/members")
    public void addMember(@RequestBody AddMemberDto addMemberDto){//@RequestHeader("Authorization") String authorizationToken,
//        String accessToken = jwtTokenProvider.resolveToken(authorizationToken);
//        String userUuid = jwtTokenProvider.parseClaims(accessToken).getSubject();
        workSpaceService.addMember(addMemberDto.getWorkspaceIdx(), addMemberDto.getMemberUuid(), addMemberDto.getPermission());
    }


    //워크스페이스 팀원 삭제
    @DeleteMapping("api/v1/workspaces/members/{workspaceIdx}")
    public void deleteMember(@PathVariable String workspaceIdx,@CookieValue String accessToken){
        String memberUUID = jwtTokenProvider.parseClaims(accessToken).getSubject();
        workSpaceService.deleteMember(workspaceIdx, memberUUID);
    }

    //워크스페이스 팀원 권한 변경
    @PatchMapping("api/v1/workspaces/members/{memberUUID}")
    public void changePermissionMember(  @PathVariable String memberUUID,@RequestBody AddMemberDto addMemberDto){
        workSpaceService.changePermissionMember(addMemberDto, memberUUID);
    }

    @PostMapping("api/v1/send-email")
    public void sendEmail(@RequestBody EmailRequest emailRequest) throws MessagingException {//@RequestHeader("Authorization") String authorizationToken,
//        String accessToken = jwtTokenProvider.resolveToken(authorizationToken);
//        String userUuid = jwtTokenProvider.parseClaims(accessToken).getSubject();
        workSpaceService.sendEmail(emailRequest);
    }


//
//    private final MemberAuthService memberAuthService;
//
//    private final JwtTokenProvider jwtTokenProvider;
//    //워크스페이스 생성
//    @PostMapping("api/v1/workspaces")
//    public void addMember(
//            @RequestHeader(name = "Authorization") String accessToken,
//            @RequestBody WorkSpace workSpace){
//        workSpaceService.addWorkSpace(workSpace);
//
//        String token = jwtTokenProvider.resolveToken(accessToken);
//        String uuid = jwtTokenProvider.parseClaims(token).getSubject();
//
//        uuid --> id
//        memberAuthService.getMemberAuth(uuid).getId;
//
//    }


}

