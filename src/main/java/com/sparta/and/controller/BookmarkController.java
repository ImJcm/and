package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.sparta.and.service.BookmarkService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/bookmark")
public class BookmarkController {

	private final BookmarkService bookmarkService;

	@PostMapping("{contestboardid}")
	public ResponseEntity<ApiResponseDto> bookmarkContest(@PathVariable Long contestboardid) {
		return bookmarkService.bookmarkContest(contestboardid);
	}

	@DeleteMapping("{contestboardid}")
	public ResponseEntity<ApiResponseDto> removeBookmark(@PathVariable Long contestboardid) {
		return bookmarkService.removeBookmark(contestboardid);
	}

}
