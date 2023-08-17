package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.CommunityPostRequestDto;
import com.sparta.and.dto.CommunityPostResponseDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communityPosts")
public class CommunityPostController {

	private final CommunityPostService communityPostService;

	// 자유게시판 글 작성
	@PostMapping
	public ResponseEntity<?> createCommunityPost(@RequestBody CommunityPostRequestDto requestDto,
	                                             @AuthenticationPrincipal UserDetailsImpl userDetails) {
		log.info("Controller - createCommunityPost : 시작");

		CommunityPostResponseDto result = communityPostService.createCommunityPost(requestDto, userDetails);

		log.info("Controller - createCommunityPost : 끝");
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}

	// 자유게시판 글 단건 조회
	@GetMapping("/{id}")
	public ResponseEntity<?> getCommunityPost(@PathVariable Long id) {
		log.info("Controller - getCommunityPost : 시작");

		CommunityPostResponseDto result = communityPostService.getCommunityPost(id);

		log.info("Controller - getCommunityPost : 끝");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}

	// 자유게시판 글 수정
	@PatchMapping("/{id}")
	public ResponseEntity<?> modifyCommunityPost(@PathVariable Long id, @RequestBody CommunityPostRequestDto requestDto) {
		log.info("Controller - editCommunityPost : 시작");

		CommunityPostResponseDto result = communityPostService.modifyCommunityPost(id, requestDto);

		log.info("Controller - editCommunityPost : 끝");
		return ResponseEntity.status(HttpStatus.OK).body(result);

	}

	// 자유게시판 글 삭제
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteCommunityPost(@PathVariable Long id) {
		log.info("Controller - deleteCommunityPost : 시작");

		ApiResponseDto result = communityPostService.deleteCommunityPost(id);

		log.info("Controller - deleteCommunityPost : 끝");
		return ResponseEntity.status(HttpStatus.OK).body(result);
	}
}
