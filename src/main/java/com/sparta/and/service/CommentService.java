package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.CommentRequestDto;
import com.sparta.and.dto.CommentResponseDto;
import com.sparta.and.entity.Comment;
import com.sparta.and.entity.User;

import java.util.List;

public interface CommentService {

    /**
     * 댓글 전체 조회
     *
     * @param boardId       조회할 보드 ID
     * @return              보드의 전체 댓글 리스트
     */
    List<CommentResponseDto> getComments(Long boardId);

    /**
     * 댓글 등록
     *
     * @param boardId               댓글을 등록할 보드 ID
     * @param user                  댓글 작성자 ID
     * @param commentRequestDto     댓글 작성 데이터
     *                              (댓글 등록 : parentId = null , 대댓글 등록 : parentId = 부모 댓글 Id)
     * @return                      요청 결과
     */
    ApiResponseDto insertComment(Long boardId, User user, CommentRequestDto commentRequestDto);

    /**
     * 댓글 수정
     *
     * @param commentId             수정할 댓글 ID
     * @param user                  수정 요청하는 유저 ID
     * @param commentRequestDto     댓글 수정 데이터
     * @return                      요청 결과
     */
    ApiResponseDto updateComment(Long commentId, User user, CommentRequestDto commentRequestDto);

    /**
     * 댓글 삭제
     *
     * @param commentId             삭제할 댓글 ID
     * @param user                  삭제 요청하는 유저 ID
     * @return                      요청 결과
     */
    ApiResponseDto deleteComment(Long commentId, User user);

    /**
     * 댓글 조회
     *
     * @param commentId         조회할 댓글 ID
     * @return                  Comment or IllegalException
     */
    Comment getCommentById(Long commentId);
}
