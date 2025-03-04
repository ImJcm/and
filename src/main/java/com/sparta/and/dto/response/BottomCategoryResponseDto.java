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

    public BottomCategoryResponseDto(BottomCategory bottomCategory){
        this.id = bottomCategory.getBottomCategoryId();
        this.categoryName = bottomCategory.getCategoryName();
    }
}
