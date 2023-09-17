package com.sparta.and.controller;

import com.sparta.and.dto.response.BottomCategoryListResponseDto;
import com.sparta.and.dto.response.BottomCategoryResponseDto;
import com.sparta.and.service.BottomCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class BottomCategoryController {

	private final BottomCategoryService bottomCategoryService;


	@GetMapping("/bottomcategory/{middleCategoryId}")
	public ResponseEntity<BottomCategoryListResponseDto> getBottomCategoryByMiddleCategoryId(
			@PathVariable(name = "middleCategoryId") Long middleCategoryId) {
		BottomCategoryListResponseDto bottomCategoryListResponse = bottomCategoryService
				.getBottomCategoryByMiddleCategoryId(middleCategoryId);
		return ResponseEntity.ok().body(bottomCategoryListResponse);
	}

}
