package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.CommunityPostRequestDto;
import com.sparta.and.dto.CommunityPostResponseDto;
import com.sparta.and.entity.CommunityPost;
import com.sparta.and.security.UserDetailsImpl;

public interface CommunityPostService {

	/**
	 * 자유게시판 글 생성
	 * @param requestDto : 작성할 내용
	 * @param userDetails : 게시글 작성 요청자
	 * @return : 생성한 보드 내용 반환(제목, 내용, 조회수)
	 */
	CommunityPostResponseDto createCommunityPost(CommunityPostRequestDto requestDto, UserDetailsImpl userDetails);

	/**
	 * 자유게시판 글 수정
	 * @param id : 수정할 게시글 id
	 * @param requestDto : 수정할 내용
	 * @return : 수정된 보드 내용 반환(제목, 내용, 조회수)
	 */
	CommunityPostResponseDto modifyCommunityPost(Long id, CommunityPostRequestDto requestDto);

	/**
	 * 자유게시판 글 삭제
	 * @param id : 삭제할 게시글 id
	 * @return : 요청 처리 메시지 + 상태코드
	 */
	ApiResponseDto deleteCommunityPost(Long id);

	/**
	 * 존재하는 글인지 검증
	 * @param id : 검증할 게시글 id
	 * @return : 존재하면 게시글 정보 반환
	 */
	CommunityPost findPost(Long id);

}
