package com.sparta.and.repository;

import com.sparta.and.entity.Comment;

import java.util.List;

public interface CommentRepositoryQuery {
    List<Comment> getCommentListFindByBoardId(Long boardId);
}
