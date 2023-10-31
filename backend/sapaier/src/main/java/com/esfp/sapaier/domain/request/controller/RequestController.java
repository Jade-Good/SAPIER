package com.esfp.sapaier.domain.request.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.esfp.sapaier.domain.request.service.RequestService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/v1/requests")
public class RequestController {
	private final RequestService requestService;

	@GetMapping("/{requestIdx}")
	public ResponseEntity<?> getRequestInfo(@PathVariable String requestIdx) {

		Map<String, Object> responseData = new HashMap<>();

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<?> createRequestInfo() {

		Map<String, Object> responseData = new HashMap<>();

		requestService.createRequest();

		return new ResponseEntity<>(responseData, HttpStatus.OK);
	}
}
