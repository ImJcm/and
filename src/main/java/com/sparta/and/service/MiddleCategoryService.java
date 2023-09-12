package com.sparta.and.service;

import com.sparta.and.dto.response.MiddleCategoryListResponseDto;
import com.sparta.and.dto.response.MiddleCategoryResponseDto;
import com.sparta.and.repository.MiddleCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MiddleCategoryService {
	private final MiddleCategoryRepository middleCategoryRepository;

	//조회
	public MiddleCategoryListResponseDto getMiddleCategory() {
		List<MiddleCategoryResponseDto> middleCategoryList = middleCategoryRepository.findAll().stream()
				.map(MiddleCategoryResponseDto::new)
				.collect(Collectors.toList());
		return new MiddleCategoryListResponseDto(middleCategoryList);
	}
}


