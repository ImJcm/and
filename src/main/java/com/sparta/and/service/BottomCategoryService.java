package com.sparta.and.service;

import com.sparta.and.dto.response.BottomCategoryListResponseDto;
import com.sparta.and.dto.response.BottomCategoryResponseDto;
import com.sparta.and.repository.BottomCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BottomCategoryService {
	private final BottomCategoryRepository bottomCategoryRepository;

	//조회
//	public BottomCategoryListResponseDto getBottomCategory() {
//		List<BottomCategoryResponseDto> bottomCategoryList = bottomCategoryRepository.findAll().stream()
//				.map(BottomCategoryResponseDto::new)
//				.collect(Collectors.toList());
//		return new BottomCategoryListResponseDto(bottomCategoryList);
//	}

	// 조회
	public BottomCategoryListResponseDto getBottomCategory() {
		List<BottomCategoryResponseDto> bottomCategoryList = bottomCategoryRepository.findAll().stream()
				.map(bottomCategory -> {
					String middleCategoryName = bottomCategory.getMiddleCategory().getCategoryName();
					return new BottomCategoryResponseDto(bottomCategory, middleCategoryName);
				})
				.collect(Collectors.toList());
		return new BottomCategoryListResponseDto(bottomCategoryList);
	}
}


