package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.request.CommentReportRequestDto;
import com.sparta.and.dto.request.CommentRequestDto;
import com.sparta.and.dto.response.CommentResponseDto;
import com.sparta.and.entity.ReportComment;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comments")
public class CommentController {
    private final CommentService commentService;

    @GetMapping("/{boardId}")
    public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.getComments(boardId, userDetails));
    }

    @PostMapping("/{boardId}")
    public ResponseEntity<ApiResponseDto> createComment(@PathVariable Long boardId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok().body(commentService.createComment(boardId,userDetails.getUser(), commentRequestDto));
    }

    @PutMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentRequestDto commentRequestDto) {
        return ResponseEntity.ok().body(commentService.updateComment(commentId, userDetails.getUser(), commentRequestDto));
    }

    @DeleteMapping("/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(commentService.deleteComment(commentId, userDetails.getUser()));
    }

    @PostMapping("/{commentId}/report")
    public ResponseEntity<ApiResponseDto> reportComment(@PathVariable Long commentId, @AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody CommentReportRequestDto commentReportRequestDto) {
        return ResponseEntity.ok().body(commentService.reportComment(commentId, userDetails.getUser(), commentReportRequestDto));
    }

}
