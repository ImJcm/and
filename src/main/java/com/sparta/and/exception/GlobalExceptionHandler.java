package com.sparta.and.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GlobalExceptionHandler {

	@ExceptionHandler({IllegalArgumentException.class})
	public ResponseEntity<?> IllegalArgumentExceptionHandler(IllegalArgumentException e) {
		return new ResponseEntity<>(
				e.getMessage(),
				HttpStatus.BAD_REQUEST
		);
	}
}
