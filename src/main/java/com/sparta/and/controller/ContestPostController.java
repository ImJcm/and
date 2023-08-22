package com.sparta.and.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.and.dto.request.ContestPostRequestDto;
import com.sparta.and.dto.response.ContestPostResponseDto;
import com.sparta.and.service.ContestPostService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contestPosts")
public class ContestPostController {

	private final ContestPostService contestPostService;

	@GetMapping("")
	public List<ContestPostResponseDto> getContests() {
		return contestPostService.getContests();
	}

	@PostMapping("")
	public ContestPostResponseDto createContest(@RequestBody ContestPostRequestDto requestDto) {
		return contestPostService.createContest(requestDto);
	}

}
