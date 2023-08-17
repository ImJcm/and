package com.sparta.and.dto;

import com.sparta.and.entity.Comment;
import com.sparta.and.entity.DeleteStatus;
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
    private List<CommentResponseDto> child = new ArrayList<>();

    public CommentResponseDto(Long id, String content, User user) {
        this.commentId = id;
        this.content = content;
        this.writer = user.getUserName();
    }

    public static CommentResponseDto convertCommentToDto(Comment comment) {
        return comment.getIsDeleted().equals(DeleteStatus.Y) ?
                new CommentResponseDto(comment.getId(), "삭제된 댓글입니다.", null) :
                new CommentResponseDto(comment.getId(), comment.getContent(), comment.getWriter());
    }

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getWriter().getUserName();
        this.child = comment.getChildren().stream().map(CommentResponseDto::new).toList();
    }
}
