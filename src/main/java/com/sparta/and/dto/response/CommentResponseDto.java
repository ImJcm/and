package com.sparta.and.dto.response;

import com.sparta.and.entity.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponseDto {
    private Long commentId;
    private String content;
    private Long step;
    private String writer;
    private String modifiedDate;
    private List<CommentResponseDto> child = new ArrayList<>();

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.step = comment.getStep();
        this.writer = comment.getWriter().getNickname();
        this.modifiedDate = comment.getModifiedDateFormatted(TimeStamped.FORMATTER_DATE_HOUR_MINUTE);
    }

    public void setContent(String content) {
        this.content = content;
    }
}
