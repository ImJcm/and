package com.sparta.and.dto.response;

import com.sparta.and.entity.Category;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryResponseDto {
	private Long id;
	private String categoryName;

    public CategoryResponseDto(Category category) {
        this.id = category.getCategoryId();
        this.categoryName = category.getCategoryName();
    }
}
