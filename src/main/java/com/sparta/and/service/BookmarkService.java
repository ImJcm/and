package com.sparta.and.service;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.sparta.and.dto.response.ApiResponseDto;
import com.sparta.and.entity.Bookmark;
import com.sparta.and.entity.ContestBoard;
import com.sparta.and.repository.BookmarkRepository;
import com.sparta.and.repository.ContestBoardRepository;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class BookmarkService {
	private final BookmarkRepository bookmarkRepository;
	private final ContestBoardRepository contestBoardRepository;

	// 북마크
	@Transactional
	public ResponseEntity<ApiResponseDto> bookmarkContest (Long id) {
		// 해당 게시글 존재 여부 확인
		ContestBoard checkContest = contestBoardRepository.findById(id).orElse(null);

		if (checkContest == null) {
			log.error("게시글이 존재하지 않습니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("공모전이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
		}

		// 이미 북마크한 게시글인지 확인
		Bookmark checkBookmark = bookmarkRepository.findByContestBoardId(id);
		if (checkBookmark != null) {
			log.error("이미 북마크한 공모전입니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("이미 북마크한 공모전입니다.", HttpStatus.BAD_REQUEST.value()));
		} else {
			Bookmark bookmark = new Bookmark(checkContest);
			bookmarkRepository.save(bookmark);
		}
		return ResponseEntity.status(200).body(new ApiResponseDto("북마크가 완료되었습니다.", HttpStatus.OK.value()));
	}

	public ResponseEntity<ApiResponseDto> removeBookmark(Long id) {
		// 해당 게시글 존재 여부 확인
		ContestBoard checkContest = contestBoardRepository.findById(id).orElse(null);

		if (checkContest == null) {
			log.error("게시글이 존재하지 않습니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("공모전이 존재하지 않습니다.", HttpStatus.BAD_REQUEST.value()));
		}

		// 이미 북마크한 게시글인지 확인
		Bookmark checkBookmark = bookmarkRepository.findByContestBoardId(id);
		if (checkBookmark == null) {
			log.error("북마크하지 않은 공모전입니다.");
			return ResponseEntity.status(400).body(new ApiResponseDto("북마크하지 않은 공모전입니다.", HttpStatus.BAD_REQUEST.value()));
		} else {
			bookmarkRepository.delete(checkBookmark);
		}
		return ResponseEntity.status(200).body(new ApiResponseDto("북마크가 해제되었습니다.", HttpStatus.OK.value()));
	}
}
