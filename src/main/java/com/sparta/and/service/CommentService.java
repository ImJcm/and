package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.CommentRequestDto;
import com.sparta.and.dto.response.CommentResponseDto;
import com.sparta.and.entity.*;
import com.sparta.and.security.UserDetailsImpl;

import java.util.List;

public interface CommentService {

    /**
     * 댓글 전체 조회
     *
     * @param boardId               조회할 보드 ID
     * @param userDetails           비밀댓글 출력용 접속 사용자 확인
     * @return                      보드의 전체 댓글 리스트
     */
    List<CommentResponseDto> getComments(Long boardId, UserDetailsImpl userDetails);

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

    /**
     * 삭제 댓글의 부모 댓글까지 삭제여부 검사
     * orphanRemoval=ture에 의해 자식~부모까지 삭제가능하다면 부모댓글을 삭제하여 자식댓글까지 모두 삭제할 수 있음
     *
     * @param comment           삭제할 수 있는지 검사할 comment
     * @return                  삭제하려는 comment
     */
    Comment getDeletableAncestorComment(Comment comment);

    /**
     * 비밀댓글 시, 댓글 작성자, 게시글 작성자, 관리자에게는 원본 댓글 보여지도록 설정
     * 삭제댓글 시, 삭제 여부에 따른 댓글 내용 설정
     *
     * @param commentResponseDto        보여질 댓글 response
     * @param secretStatus              비밀댓글 여부
     * @param deleteStatus              삭제댓글 여부
     * @param writer                    댓글 작성자
     * @param post                      게시글 작성자 확인
     * @param connectUser               현재 접속 유저
     */
    void convertComment(CommentResponseDto commentResponseDto, SecretStatus secretStatus, DeleteStatus deleteStatus, User writer, Post post, UserDetailsImpl connectUser);

    /**
     * 비밀 댓글 여부 확인
     *
     * secretStatus.equals(SecretStatus.Y)                                      :   비밀 댓글 상태 확인
     * connectUser == null                                                      :   비로그인 유저인 경우
     * connectUser.getUser().getUserId().equals(writer.getUserId())             :   접속유저가 댓글 작성자인 경우
     * connectUser.getUser().getUserId().equals(post.getUser().getUserId())     :   접속유저가 게시글 작성자인 경우
     *
     * @param writer                    댓글 작성자
     * @param post                      게시글 작성자 확인용
     * @param connectUser               현재 접속 사용자
     * @param secretStatus              비밀 댓글 여부
     * @return                          비밀댓글(True) or 원본 댓글 내용(False) 결정
     *
     */
    Boolean checkSecretComment(User writer, Post post, UserDetailsImpl connectUser, SecretStatus secretStatus);

    /**
     * 댓글 삭제 여부 확인
     *
     * @param deleteStatus          댓글 삭제여부 (Y or N)
     * @return                      True or False
     */
    Boolean checkDeleteComment(DeleteStatus deleteStatus);
}
