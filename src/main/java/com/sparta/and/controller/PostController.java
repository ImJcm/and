package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.PostRequestDto;
import com.sparta.and.dto.response.PostResponseDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/posts")
public class PostController {

	private final PostService postService;

	// 자유게시판 글 작성
	@PostMapping
	public ResponseEntity<?> createPost(@RequestBody PostRequestDto requestDto,
	                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
		log.info("Controller - createPost : 시작");

		PostResponseDto result = postService.createPost(requestDto, userDetails);

		log.info("Controller - createPost : 끝");
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	// 자유게시판 글 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getPost(@PathVariable Long id) {
		log.info("Controller - getPost : 시작");

		PostResponseDto result = postService.getPost(id);

		log.info("Controller - getPost : 끝");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	// 자유게시판 글 수정
	@PatchMapping("/{id}")
	public ResponseEntity<?> modifyPost(@PathVariable Long id, @RequestBody PostRequestDto requestDto) {
		log.info("Controller - editPost : 시작");

		PostResponseDto result = postService.modifyPost(id, requestDto);

		log.info("Controller - editPost : 끝");
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}

	// 자유게시판 글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePost(@PathVariable Long id) {
		log.info("Controller - deletePost : 시작");

		ApiResponseDto result = postService.deletePost(id);

		log.info("Controller - deletePost : 끝");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
