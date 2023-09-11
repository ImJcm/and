package com.sparta.and.controller;

import java.util.List;

import com.sparta.and.dto.request.ContestRequestDto;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.and.dto.response.ContestResponseDto;
import com.sparta.and.service.ContestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contestPosts")
public class ContestController {

	private final ContestService contestService;

	@GetMapping("")
	public List<ContestResponseDto> getContests() {
		return contestService.getContests();
	}

	@PostMapping("")
	public ContestResponseDto createContest(@RequestBody ContestRequestDto requestDto) {
		return contestService.createContest(requestDto);
	}

}
