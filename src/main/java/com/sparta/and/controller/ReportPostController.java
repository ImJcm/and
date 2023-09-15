package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.service.ReportPostService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.and.dto.request.ReportPostRequestDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.ReportPostService;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class ReportPostController {

	private final ReportPostService reportPostService;


	@PostMapping("/{postId}/report")
	public ResponseEntity<ApiResponseDto> ReportPost(@PathVariable Long postId,
		@RequestBody ReportPostRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return reportPostService.ReportPost(postId, requestDto, userDetails.getUser());
	}
}
