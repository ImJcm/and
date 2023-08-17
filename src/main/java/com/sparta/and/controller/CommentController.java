package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.CommentRequestDto;
import com.sparta.and.dto.CommentResponseDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{board_id}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long communityPostId) {
        return ResponseEntity.ok().body(commentService.getComments(communityPostId));
    }

    @PostMapping("/{board_id}")
    public ResponseEntity<ApiResponseDto> createComment(@PathVariable Long board_id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok().body(commentService.insertComment(board_id,userDetails.getUser(), commentRequestDto));
    }

    @PutMapping("/{comment_id}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok().body(commentService.updateComment(comment_id, userDetails.getUser(), commentRequestDto));
    }

    @DeleteMapping("/{comment_id}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long comment_id, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.deleteComment(comment_id, userDetails.getUser()));
    }

}
