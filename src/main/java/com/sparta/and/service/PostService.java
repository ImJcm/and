package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.PostRequestDto;
import com.sparta.and.dto.response.PostResponseDto;
import com.sparta.and.entity.Post;
import com.sparta.and.security.UserDetailsImpl;
import org.springframework.data.domain.Page;

import java.util.List;

public interface PostService {

	/**
	 * 자유게시판 글 생성
	 *
	 * @param requestDto  : 작성할 내용
	 * @param userDetails : 게시글 작성 요청자
	 * @return : 생성한 보드 내용 반환(제목, 내용, 조회수)
	 */
	PostResponseDto createPost(PostRequestDto requestDto, UserDetailsImpl userDetails);

	/**
	 * 자유게시판 글 단건 조회
	 *
	 * @param id : 가져올 게시글 id
	 * @return : 게시글 정보 반환
	 */
	PostResponseDto getPost(Long id);

	/**
	 * 자유게시판 글 수정
	 *
	 * @param id         : 수정할 게시글 id
	 * @param requestDto : 수정할 내용
	 * @return : 수정된 보드 내용 반환(제목, 내용, 조회수)
	 */
	PostResponseDto modifyPost(Long id, PostRequestDto requestDto);

	/**
	 * 자유게시판 글 삭제
	 *
	 * @param id : 삭제할 게시글 id
	 * @return : 요청 처리 메시지 + 상태코드
	 */
	ApiResponseDto deletePost(Long id);

	/**
	 * 존재하는 글인지 검증
	 *
	 * @param id : 검증할 게시글 id
	 * @return : 존재하면 게시글 정보 반환
	 */
	Post findPost(Long id);

	/**
	 * 모든 자유게시글 조회
	 *
	 * @return			모든 자유게시글 반환
	 */
    Page<PostResponseDto> getPosts(int page, int size);
}
