package com.sparta.and.service;



import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.BoardRequestDto;
import com.sparta.and.dto.response.BoardResponseDto;
import com.sparta.and.entity.Board;

import java.util.List;

public interface BoardService {

	/**
	 * 게시글 전체 조회
	 *
	 * @return : 게시글 정보 반환
	 */
	List<BoardResponseDto> getAllBoards();
}

