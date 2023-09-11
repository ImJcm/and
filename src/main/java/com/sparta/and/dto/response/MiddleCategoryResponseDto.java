package com.sparta.and.dto.response;

import com.sparta.and.entity.MiddleCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MiddleCategoryResponseDto {
    private Long id;
    private String categoryName;

    public MiddleCategoryResponseDto(MiddleCategory middleCategory) {
        this.id = middleCategory.getMiddleCategoryId();
        this.categoryName = middleCategory.getCategoryName();
    }
}
