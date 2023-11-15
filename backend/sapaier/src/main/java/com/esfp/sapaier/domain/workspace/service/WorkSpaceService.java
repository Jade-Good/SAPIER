package com.esfp.sapaier.domain.workspace.service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.esfp.sapaier.domain.user.repository.UserRepository;
import com.esfp.sapaier.domain.workspace.document.WorkSpace;
import com.esfp.sapaier.domain.workspace.dto.AddMemberDto;
import com.esfp.sapaier.domain.workspace.dto.CollectionListDto;
import com.esfp.sapaier.domain.workspace.dto.EmailRequest;
import com.esfp.sapaier.domain.workspace.dto.UserDataDto;
import com.esfp.sapaier.domain.workspace.dto.UserPermissionDto;
import com.esfp.sapaier.domain.workspace.exception.NoWorkspaceException;
import com.esfp.sapaier.domain.workspace.repository.UserDataRepository;
import com.esfp.sapaier.domain.workspace.repository.WorkSpaceRepository;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class WorkSpaceService {
	private final WorkSpaceRepository workSpaceRepository;
	private final UserDataRepository userDataRepository;
	private final UserRepository userRepository;
	private final JavaMailSender emailSender;

	private final String NO_WORKSPACE_EXCEPTION = "해당하는 워크스페이스가 존재하지 않습니다.";

	public List<WorkSpace> WorkSpaceList(String uuid) {
		return workSpaceRepository.findWorkSpaceList(uuid);
	}

	public WorkSpace addWorkSpace(WorkSpace workSpace) {

		WorkSpace workSpaceData = workSpaceRepository.save(workSpace);
		return workSpaceData;
	}

	public void updateWorkSpace(WorkSpace workSpace) {
		workSpaceRepository.save(workSpace);
	}

	public void deleteWorkSpace(String workSpaceIdx) {
		workSpaceRepository.deleteById(workSpaceIdx);
	}

	public WorkSpace infoWorkSpace(String workSpaceIdx) {
		Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workSpaceIdx);

		if (optionalWorkSpace.isPresent()) {
			WorkSpace workSpace = optionalWorkSpace.get();
			return workSpace;
		} else {
			return null;
		}
	}

	//test : member 1명씩 찾기
	public List<UserDataDto> searhMember(String workSpaceIdx) {
		WorkSpace workSpace = workSpaceRepository.findById(workSpaceIdx)
			.orElseThrow(() -> new NoSuchElementException("정보가 존재하지 않습니다"));
		List<UserDataDto> MemberList = new ArrayList<>();

		for (int i = 0; i < workSpace.getMemberList().size(); i++) {
			MemberList.add(userDataRepository.searchMember(workSpace.getMemberList().get(i).getUuId()));
			System.out.println(workSpace.getMemberList().get(i).getUuId());

		}
		System.out.println(MemberList);
		return MemberList;
	}

	public void addMember(String workspaceidx, String memberuuId, String memberPermission) {
		//        workSpaceRepository.deleteUserPermissionByWorkspaceIdxAndUuId(workspaceidx,memberuuId);
		//        workSpaceRepository.deleteByWorkspaceIdxAndUuId(workspaceidx, memberuuId);
		Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(workspaceidx);

		if (optionalWorkSpace.isPresent()) {
			WorkSpace workSpace = optionalWorkSpace.get();
			//            System.out.println(workSpace);
			List<UserPermissionDto> userList = workSpace.getMemberList();
			userList.add(new UserPermissionDto(memberuuId, memberPermission));
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
		WorkSpace workSpace = workSpaceRepository.findById(workspaceidx)
			.orElseThrow(() -> new NoSuchElementException("deleteMember에서 findById 존재하지 않습니다"));

		if (workSpace != null) {
			System.out.println(workSpace);
			List<UserPermissionDto> userList = workSpace.getMemberList();
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getUuId().equals(memberuuId)) {
					userList.remove(i);
				}
			}
			System.out.println("ifans skdhadddddddd");

			workSpaceRepository.save(workSpace);
			//            return  workSpace;

		}
		System.out.println("if out");

		//        else {
		//
		//            return  null;
		//        }

	}

	public void changePermissionMember(AddMemberDto addMemberDto, String memberUUID) {
		Optional<WorkSpace> optionalWorkSpace = workSpaceRepository.findById(addMemberDto.getWorkspaceIdx());

		if (optionalWorkSpace.isPresent()) {
			WorkSpace workSpace = optionalWorkSpace.get();
			//            System.out.println(workSpace);
			List<UserPermissionDto> userList = workSpace.getMemberList();
			for (int i = 0; i < userList.size(); i++) {
				if (userList.get(i).getUuId().equals(memberUUID)) {
					userList.set(i, new UserPermissionDto(addMemberDto.getMemberUuid(), addMemberDto.getPermission()));
				}
			}
			workSpaceRepository.save(workSpace);
			//            return  workSpace;

		}
	}

	public String searchWorkspaceName(String workspaceIdx) {
		WorkSpace workSpace = workSpaceRepository.findById(workspaceIdx)
			.orElseThrow(() -> new NoWorkspaceException(NO_WORKSPACE_EXCEPTION));
		return workSpace.getName();
	}

	public void sendEmail(EmailRequest emailRequest) throws MessagingException {
		//        UserDto userDto = userRepository.find
		String htmlBody = generateHtmlBody(emailRequest.getWorkspaceIdx());
		MimeMessage message = emailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

		helper.setTo(emailRequest.getTo());
		helper.setSubject(emailRequest.getSubject());
		helper.setText(htmlBody, true); // 두 번째 매개변수를 true로 설정하여 HTML 형식으로 전송

		emailSender.send(message);
	}

	public String generateHtmlBody(String workspaceidx) {
		return
			"<html><body><h1>invite workspace</h1><p>인증 버튼:</p> <a href=\"https://sapier.co.kr/workspace/invite?key="
				+ workspaceidx + "\" target=\"_blank\">초대 링크</a></body></html>";

	}

	public void addCollectionDocument(String workspaceId, CollectionListDto collectionListDto) {
		WorkSpace workSpace = workSpaceRepository.findById(workspaceId)
			.orElseThrow(() -> new NoWorkspaceException(NO_WORKSPACE_EXCEPTION));

		List<CollectionListDto> collectionListDtoList = workSpace.getCollectionList();
		//        log.info("찍어보기: {} ", collectionListDtoList);
		if (collectionListDtoList == null) {
			collectionListDtoList = new ArrayList<>();
		}
		collectionListDtoList.add(collectionListDto);
		//        log.info("디티오 리스트: {}" , collectionListDtoList);
		//        workSpace.update(workSpace,collectionListDtoList);
		workSpaceRepository.save(new WorkSpace(workSpace, collectionListDtoList));
	}

}

