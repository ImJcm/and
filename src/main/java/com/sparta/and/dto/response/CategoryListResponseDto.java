package com.sparta.and.dto.response;

import lombok.Getter;

import java.util.List;

@Getter
public class CategoryListResponseDto {

    private List<CategoryResponseDto> categoryList;

    public CategoryListResponseDto(List<CategoryResponseDto>categoryList){
        this.categoryList=categoryList;
    }
}
