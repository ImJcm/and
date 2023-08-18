package com.sparta.and.dto.response;

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
    private String createdDate;
    private List<CommentResponseDto> child = new ArrayList<>();

    public CommentResponseDto(Long id, String content, User user, String createdDate) {
        this.commentId = id;
        this.content = content;
        this.writer = user.getUserName();
        this.createdDate = createdDate;
    }

    public static CommentResponseDto convertCommentToDto(Comment comment) {
        return comment.getIsDeleted().equals(DeleteStatus.Y) ?
                new CommentResponseDto(comment.getId(), "삭제된 댓글입니다.", comment.getWriter(), comment.getCreatedDateFormatted()) :
                new CommentResponseDto(comment.getId(), comment.getContent(), comment.getWriter(), comment.getCreatedDateFormatted());
    }

    public CommentResponseDto(Comment comment) {
        this.commentId = comment.getId();
        this.content = comment.getContent();
        this.writer = comment.getWriter().getUserName();
        this.child = comment.getChildren().stream().map(CommentResponseDto::new).toList();
    }
}
