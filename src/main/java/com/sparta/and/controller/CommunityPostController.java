package com.sparta.and.controller;

import com.sparta.and.dto.CommunityPostRequestDto;
import com.sparta.and.dto.CommunityPostResponseDto;
import com.sparta.and.service.CommunityPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/communityPosts")
public class CommunityPostController {

	private final CommunityPostService communityPostService;

	// 자유게시판 글 작성
	@PostMapping
	public ResponseEntity<?> createCommunityPost(@RequestBody CommunityPostRequestDto requestDto) {
		log.info("Controller - createCommunityPost");
		CommunityPostResponseDto result = communityPostService.createCommunityPost(requestDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(result);
	}
}
