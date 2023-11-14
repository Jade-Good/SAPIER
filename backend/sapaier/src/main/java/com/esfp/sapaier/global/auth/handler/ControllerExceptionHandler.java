package com.esfp.sapaier.global.auth.handler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ControllerExceptionHandler {

	@ExceptionHandler(Exception.class)
	public ResponseEntity handleException(Exception exception) {
		return ResponseEntity
			.status(500)
			.body(exception.getMessage());
	}
}
