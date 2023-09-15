package com.sparta.and.controller;

import com.sparta.and.dto.response.BottomCategoryListResponseDto;
import com.sparta.and.service.BottomCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class BottomCategoryController {

	private final BottomCategoryService bottomCategoryService;

	//조회
	@GetMapping("/bottomcategory")
	public ResponseEntity<BottomCategoryListResponseDto> getBottomCategory() {
		BottomCategoryListResponseDto getBottomCategory = bottomCategoryService.getBottomCategory();
		return ResponseEntity.ok().body(getBottomCategory);
	}
}
