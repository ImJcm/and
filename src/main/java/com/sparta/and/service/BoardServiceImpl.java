package com.sparta.and.service;


import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.BoardRequestDto;
import com.sparta.and.dto.response.BoardResponseDto;
import com.sparta.and.entity.Board;
import com.sparta.and.repository.BoardRepository;
import com.sparta.and.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j(topic = "BoardService")
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService {

	private final BoardRepository boardRepository;
	private final CategoryRepository categoryRepository;

	@Override
	public List<BoardResponseDto> getAllBoards() {
		List<Board> boards = boardRepository.findAll();
		return boards.stream().map(BoardResponseDto::new).collect(Collectors.toList());
	}
}