package com.sparta.and.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.and.dto.request.ReportPostRequestDto;
import com.sparta.and.dto.response.ApiResponseDto;
import com.sparta.and.service.ReportPostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/posts")
@RestController
public class ReportPostController {

	private final ReportPostService reportPostService;

	@PostMapping("/{postid}")
	public ResponseEntity<ApiResponseDto> ReportPost(@PathVariable Long postid, @RequestBody ReportPostRequestDto requestDto) {
		return reportPostService.ReportPost(postid, requestDto);
	}
}
