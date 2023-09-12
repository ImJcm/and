package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sparta.and.dto.request.ReportPostRequestDto;
import com.sparta.and.entity.Post;
import com.sparta.and.entity.ReportPost;
import com.sparta.and.entity.User;
import com.sparta.and.repository.PostRepository;
import com.sparta.and.repository.ReportPostRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class ReportPostService {

	private final ReportPostRepository reportPostRepository;
	private final PostRepository postRepository;

	// 신고하기
	@Transactional
	public ResponseEntity<ApiResponseDto> ReportPost (Long id, ReportPostRequestDto requestDto, User reporter) {
		String reportReason = requestDto.getReportReason();

		// 해당 게시글 존재 여부 확인
		Post checkPost = postRepository.findById(id).orElse(null);

		if (checkPost == null) {
			log.error("게시글이 존재하지 않습니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("신고할 게시글이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
		}

		// 이미 신고한 게시글인지 확인
		ReportPost checkReportLog = reportPostRepository.findByUserUserIdAndPostId(reporter.getUserId(),id);
		if (checkReportLog != null) {
			log.error("이미 신고한 게시글입니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("이미 신고한 게시글입니다.", HttpStatus.BAD_REQUEST.value()));
		} else {
			ReportPost reportPost = new ReportPost(checkPost,reportReason, reporter);
			reportPostRepository.save(reportPost);
		}
		return ResponseEntity.status(200).body(new ApiResponseDto("게시글 신고가 완료되었습니다.", HttpStatus.OK.value()));
	}

}
