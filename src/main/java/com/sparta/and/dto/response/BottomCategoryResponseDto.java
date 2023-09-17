package com.sparta.and.dto.response;

import com.sparta.and.entity.BottomCategory;
import com.sparta.and.entity.MiddleCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BottomCategoryResponseDto {
    private Long id;
    private Long categoryId;
    private String categoryName;

    public BottomCategoryResponseDto(BottomCategory bottomCategory){
        this.id = bottomCategory.getBottomCategoryId();
        this.categoryId = bottomCategory.getMiddleCategory().getMiddleCategoryId();
        this.categoryName = bottomCategory.getCategoryName();
    }
}
