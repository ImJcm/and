package com.sparta.and.controller;

import com.sparta.and.dto.response.BoardResponseDto;
import com.sparta.and.service.BoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j(topic = "BoardController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/posts")
public class BoardController {

	private final BoardService boardService;

	//글 전체조회
	@GetMapping("/getAll")
	public ResponseEntity<List<BoardResponseDto>> getAllBoards() {
		List<BoardResponseDto> boards = boardService.getAllBoards();
		return ResponseEntity.ok(boards);
	}
}


