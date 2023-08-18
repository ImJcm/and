package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.CommentRequestDto;
import com.sparta.and.dto.response.CommentResponseDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
	private final CommentService commentService;

	@GetMapping("/{boardId}")
	public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long boardId) {
		return ResponseEntity.ok().body(commentService.getComments(boardId));
	}

	@PostMapping("/{boardId}")
	public ResponseEntity<ApiResponseDto> createComment(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
		return ResponseEntity.ok().body(commentService.insertComment(boardId, userDetails.getUser(), commentRequestDto));
	}

	@PutMapping("/{commentId}")
	public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
		return ResponseEntity.ok().body(commentService.updateComment(commentId, userDetails.getUser(), commentRequestDto));
	}

	@DeleteMapping("/{commentId}")
	public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
		return ResponseEntity.ok().body(commentService.deleteComment(commentId, userDetails.getUser()));
	}

}
