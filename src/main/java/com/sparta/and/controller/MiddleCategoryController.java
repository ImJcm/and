package com.sparta.and.controller;

import com.sparta.and.dto.response.MiddleCategoryListResponseDto;
import com.sparta.and.service.MiddleCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class MiddleCategoryController {

	private final MiddleCategoryService middleCategoryService;

	//조회
	@GetMapping("/middlecategory")
	public ResponseEntity<MiddleCategoryListResponseDto> getMiddleCategory() {
		MiddleCategoryListResponseDto getMiddleCategory = middleCategoryService.getMiddleCategory();
		return ResponseEntity.ok().body(getMiddleCategory);
	}
}
