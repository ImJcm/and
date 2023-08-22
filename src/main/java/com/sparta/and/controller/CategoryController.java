package com.sparta.and.controller;

import com.sparta.and.dto.response.CategoryListResponseDto;
import com.sparta.and.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class CategoryController {

    private final CategoryService categoryService;


    // 카테고리 전체조회
    @GetMapping("/category")
    public ResponseEntity<CategoryListResponseDto> getCategory() {
        CategoryListResponseDto getCategory = categoryService.getCategory();
        return ResponseEntity.ok().body(getCategory);
    }

}



