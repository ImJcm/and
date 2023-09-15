package com.sparta.and.dto.response;

import com.sparta.and.entity.BottomCategory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class BottomCategoryResponseDto {
    private Long id;
    private String categoryName;
    private String middleCategoryName;

//    public BottomCategoryResponseDto(BottomCategory bottomCategory){
//        this.id = bottomCategory.getBottomCategoryId();
//        this.categoryName = bottomCategory.getCategoryName();
//    }
public BottomCategoryResponseDto(BottomCategory bottomCategory, String middleCategoryName) {
    this.id = bottomCategory.getBottomCategoryId();
    this.categoryName = bottomCategory.getCategoryName();
    this.middleCategoryName = middleCategoryName; // MiddleCategory의 categoryName 설정
}
}
