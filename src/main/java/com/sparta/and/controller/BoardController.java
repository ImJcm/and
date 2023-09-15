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

@Slf4j(topic = "BoardController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/posts")
public class BoardController {

	private final BoardService boardService;

	// 글 단건 조회
	@GetMapping("/{categoryId}/{boardId}")
	public ResponseEntity<?> getBoard(@PathVariable Long categoryId,
	                                  @PathVariable Long boardId) {
		log.info("Controller - getBoard : 시작");

		BoardResponseDto result = boardService.getBoard(categoryId, boardId);

		log.info("Controller - getBoard : 끝");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}

