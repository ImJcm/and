package com.sparta.and.dto.response;

import com.sparta.and.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {
    private Long categoryId;
    private String categoryName;

    public CategoryResponseDto(Category category){
        this.categoryId = category.getId();
        this.categoryName = category.getCategoryName();

    }
}
