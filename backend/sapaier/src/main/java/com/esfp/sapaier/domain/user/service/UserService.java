package com.esfp.sapaier.domain.user.service;

import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.esfp.sapaier.domain.user.model.dto.UserDto;
import com.esfp.sapaier.domain.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	private final UserRepository userRepository;

	public UserDto getUserInfo(String key) {
		return userRepository
			.findUserByKey(key)
			.orElseThrow(() -> new NoSuchElementException("회원정보가 존재하지 않습니다"))
			.convertToDto();
	}

}
