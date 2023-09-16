package com.sparta.and.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.sparta.and.dto.response.CategoryListResponseDto;
import com.sparta.and.dto.response.CategoryResponseDto;
import com.sparta.and.repository.CategoryRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CategoryService {
	private final CategoryRepository categoryRepository;

	//카테고리 전체조회
	public CategoryListResponseDto getCategory() {
		List<CategoryResponseDto> categoryList = categoryRepository.findAll().stream()
			.map(CategoryResponseDto::new)
			.collect(Collectors.toList());

		return new CategoryListResponseDto(categoryList);
	}

	// public CategoryResponseDto getCategoryResponseDtoByCategoryName(String categoryName) {
	// 	Category category = categoryRepository.findByCategoryName(categoryName);
	// 	if (category != null) {
	// 		return new CategoryResponseDto(category);
	// 	}
	// 	return null;
	// }
}
