package com.sparta.and.controller;

import java.util.List;

import com.sparta.and.dto.SearchRequestDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.sparta.and.dto.response.ContestResponseDto;
import com.sparta.and.service.ContestService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contest")
public class ContestController {

	private final ContestService contestService;

	@GetMapping("")
	public List<ContestResponseDto> getContests() {
		return contestService.getContests();
	}

	@GetMapping("/{contestId}")
	public ResponseEntity<?> getContest(@PathVariable Long contestId) {
		ContestResponseDto result = contestService.getContest(contestId);

		return ResponseEntity.status(HttpStatus.OK).body(result);
	}


	@PostMapping("/search")
	public List<ContestResponseDto> searchContest(@RequestBody SearchRequestDto requestDto) {
		return contestService.searchContest(requestDto);
	}
}
