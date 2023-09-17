package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.BoardRequestDto;
import com.sparta.and.dto.response.BoardResponseDto;
import com.sparta.and.dto.response.CategoryResponseDto;
import com.sparta.and.entity.Board;
import com.sparta.and.entity.Category;
import com.sparta.and.entity.MiddleCategory;

import java.util.List;

import org.springframework.data.domain.Page;

public interface BoardService {

	/**
	 * 게시글 전체 조회
	 *
	 * @return : 게시글 정보 반환
	 */
	Page<BoardResponseDto> getAllBoards(int page, int size);

	/**
	 * 게시글 단건 조회
	 *
	 * @param id : 가져올 게시글 id
	 * @return : 게시글 정보 반환
	 */
	BoardResponseDto getBoard(Long id);


	/**
	 * 존재하는 카테고리인지 검증
	 *
	 * @param categoryId : 검증할 카테고리 id
	 */
	void findCategory(MiddleCategory categoryId);

	/**
	 * 존재하는 게시글인지 검증
	 *
	 * @param boardId : 검증할 게시글 id
	 * @return 존재하면 게시글 정보 반환
	 */
	Board findBoard(Long boardId);


}