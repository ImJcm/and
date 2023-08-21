package com.sparta.and.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.and.dto.request.ContestBoardRequestDto;
import com.sparta.and.dto.response.ContestBoardResponseDto;
import com.sparta.and.service.ContestBoardService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contestBoards")
public class ContestBoardController {

	private final ContestBoardService contestBoardService;

	@GetMapping("")
	public List<ContestBoardResponseDto> getContests() {
		return contestBoardService.getContests();
	}

	@PostMapping("")
	public ContestBoardResponseDto createContest(@RequestBody ContestBoardRequestDto requestDto) {
		return contestBoardService.createContest(requestDto);
	}

}
