package com.sparta.and.dto.response;

import com.sparta.and.entity.Comment;
import com.sparta.and.entity.DeleteStatus;
import com.sparta.and.entity.SecretStatus;
import com.sparta.and.entity.User;
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
    private String writer;
    private String createdDate;
    private List<CommentResponseDto> child = new ArrayList<>();

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getWriter().getNickname();
        this.createdDate = comment.getCreatedDateFormatted();
    }

    public void setContent(String content) {
        this.content = content;
    }
}
