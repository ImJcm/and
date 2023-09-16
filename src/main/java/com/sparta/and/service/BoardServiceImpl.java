package com.sparta.and.service;


import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.BoardRequestDto;
import com.sparta.and.dto.response.BoardResponseDto;
import com.sparta.and.dto.response.CategoryResponseDto;
import com.sparta.and.entity.Board;
import com.sparta.and.entity.Category;
import com.sparta.and.entity.MiddleCategory;
import com.sparta.and.repository.BoardRepository;
import com.sparta.and.repository.CategoryRepository;
import com.sparta.and.repository.MiddleCategoryRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "BoardService")
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final MiddleCategoryRepository middleCategoryRepository;

	@Override
	public Page<BoardResponseDto> getAllBoards(int page, int size) {
		Pageable pageable = PageRequest.of(page, size);
		Page<Board> boards = boardRepository.findAll(pageable);
		return  boards.map(BoardResponseDto::new);
	}

	@Override
	public BoardResponseDto getBoard(Long id){
		Board board = findBoard(id);

		long views = board.getNoticeViews() + 1;
		board.setNoticeViews(views);

		return new BoardResponseDto(board);
	}

	@Override
	public void findCategory(MiddleCategory categoryId) {
		middleCategoryRepository.findById(categoryId.getMiddleCategoryId()).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 카테고리입니다.")
		);
	}


	@Override
	public Board findBoard(Long boardId) {
		return boardRepository.findById(boardId).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 글입니다.")
		);
	}


	// @Override
	// public Page<BoardResponseDto> getAllNotices(CategoryResponseDto categoryDto, int page, int size) {
	// 	if (categoryDto != null && "공지사항".equals(categoryDto.getCategoryName())) {
	// 		Pageable pageable = PageRequest.of(page, size);
	// 		Page<Board> notices = boardRepository.findByCategoryId(categoryDto.getId(), pageable);
	// 		return notices.map(BoardResponseDto::new);
	// 	}
	// 	return Page.empty();
	// }
	//
	// @Override
	// public Page<BoardResponseDto> getAllEvents(CategoryResponseDto categoryDto, int page, int size) {
	// 	if ("이벤트".equals(categoryDto.getCategoryName())) {
	// 		Pageable pageable = PageRequest.of(page, size);
	// 		Page<Board> events = boardRepository.findByCategoryId(categoryDto.getId(), pageable);
	// 		return events.map(BoardResponseDto::new);
	// 	}
	// 	return Page.empty();
	// }
}
