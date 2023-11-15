package com.esfp.sapaier.domain.workspace.controller;


import com.esfp.sapaier.domain.workspace.document.WorkSpace;
import com.esfp.sapaier.domain.workspace.dto.EmailRequest;
import com.esfp.sapaier.domain.workspace.dto.UserDataDto;
import com.esfp.sapaier.domain.workspace.dto.AddMemberDto;
import com.esfp.sapaier.domain.workspace.dto.CollectionListDto;
import com.esfp.sapaier.domain.workspace.dto.UserPermissionDto;
import com.esfp.sapaier.domain.workspace.service.WorkSpaceService;
import com.esfp.sapaier.global.auth.service.UserAuthService;
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
    private final UserAuthService userAuthService;


    //workspace 목록 조회
    @GetMapping("api/v1/workspaces")
    public List<WorkSpace> WorkSpaceList(
            @RequestHeader(name = "Authorization",required = false) String bearerToken,
            @CookieValue(name = "accessToken",required = false) String accessToken){//@RequestBody String uuId
        String userUuid = userAuthService.getUserUuid(accessToken,bearerToken);
        return workSpaceService.WorkSpaceList(userUuid);
    }


    //워크스페이스 생성
    @PostMapping("api/v1/workspaces")
    public WorkSpace addWorkSpace(@RequestBody WorkSpace workSpace, @RequestHeader(name = "Authorization",required = false) String bearerToken,
                             @CookieValue(name = "accessToken",required = false) String accessToken){//@RequestBody String uuId
        String userUuid = userAuthService.getUserUuid(accessToken,bearerToken);
        List<UserPermissionDto> list = new ArrayList<>();
        UserPermissionDto userPermissionDto = new UserPermissionDto();
        userPermissionDto.setUserPermission("admin");
        userPermissionDto.setUuId(userUuid);
        list.add(userPermissionDto);
        workSpace.setMemberList(list);
        workSpace.setAdmin(userUuid);

        return workSpaceService.addWorkSpace(workSpace);
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
    @PostMapping("api/v1/workspaces/{key}")
    public void addMember(@PathVariable String key, @RequestHeader(name = "Authorization",required = false) String bearerToken,
                          @CookieValue(name = "accessToken",required = false) String accessToken){//@RequestBody String uuId
        String userUuid = userAuthService.getUserUuid(accessToken,bearerToken);

        workSpaceService.addMember(key, userUuid, "viewer");
    }



    //워크스페이스 팀원 삭제
    @DeleteMapping("api/v1/workspaces/members/{workspaceIdx}")
    public void deleteMember(@PathVariable String workspaceIdx,@RequestHeader(name = "Authorization",required = false) String bearerToken,
                             @CookieValue(name = "accessToken",required = false) String accessToken){//@RequestBody String uuId
        String userUuid = userAuthService.getUserUuid(accessToken,bearerToken);
        workSpaceService.deleteMember(workspaceIdx, userUuid);
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


    @PostMapping("/api/v1/workspaces/collection/{workspaceId}")
    ResponseEntity<Object> addCollectionDocument(@PathVariable String workspaceId, @RequestBody CollectionListDto collectionListDto){
        workSpaceService.addCollectionDocument(workspaceId,collectionListDto);
        return new ResponseEntity<>(HttpStatus.OK);
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

