package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.BoardRequestDto;
import com.sparta.and.dto.response.BoardResponseDto;
import com.sparta.and.dto.response.CategoryResponseDto;
import com.sparta.and.dto.response.PostResponseDto;
import com.sparta.and.entity.Category;
import com.sparta.and.service.BoardService;
import com.sparta.and.service.CategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j(topic = "BoardController")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/admin/boards")
public class BoardController {

	private final BoardService boardService;
	private final CategoryService categoryService;


	// 글 전체조회
	@GetMapping("/notices")
	public ResponseEntity<Page<BoardResponseDto>> getAllBoards(
		@RequestParam("page") int page,
		@RequestParam("size") int size) {
		return ResponseEntity.ok().body(boardService.getAllBoards(page-1,size));
	}

	// 단건 조회
	@GetMapping("/notice")
	public ResponseEntity<?> getBoard(@PathVariable Long id) {

		BoardResponseDto responseDto = boardService.getBoard(id);

		return ResponseEntity.status(HttpStatus.OK).body(responseDto);
	}


	// @GetMapping("/notices")
	// public ResponseEntity<Page<BoardResponseDto>> getAllNotices(
	// 	@RequestParam("page") int page,
	// 	@RequestParam("size") int size) {
	// 	CategoryResponseDto categoryDto = categoryService.getCategoryResponseDtoByCategoryName("공지사항");
	// 	return ResponseEntity.ok().body(boardService.getAllNotices(categoryDto, page - 1, size));
	// }
	//
	// @GetMapping("/events")
	// public ResponseEntity<Page<BoardResponseDto>> getAllEvents(
	// 	@RequestParam("page") int page,
	// 	@RequestParam("size") int size) {
	// 	CategoryResponseDto categoryDto = categoryService.getCategoryResponseDtoByCategoryName("이벤트");
	// 	return ResponseEntity.ok().body(boardService.getAllEvents(categoryDto, page - 1, size));
	// }
}