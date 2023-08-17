package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.CommunityPostRequestDto;
import com.sparta.and.dto.CommunityPostResponseDto;
import com.sparta.and.entity.CommunityPost;
import com.sparta.and.repository.CommunityPostRepository;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j(topic = "CommunityPostService")
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

	private final CommunityPostRepository communityPostRepository;

	@Override
	public CommunityPostResponseDto createCommunityPost(CommunityPostRequestDto requestDto, UserDetailsImpl userDetails) {
		log.info("Service - createCommunityPost : 시작");
		CommunityPost communityPost = communityPostRepository.save(new CommunityPost(requestDto, userDetails.getUser()));
		log.info("Service - createCommunityPost : 끝");
		return new CommunityPostResponseDto(communityPost);
	}

	@Override
	@Transactional
	public CommunityPostResponseDto modifyCommunityPost(Long id, CommunityPostRequestDto requestDto) {
		log.info("Service - modifyCommunityPost : 시작");

		CommunityPost communityPost = findPost(id);

		// TODO : 작성자 확인 추가
		communityPost.setTitle(requestDto.getTitle());
		communityPost.setContents(requestDto.getContents());

		log.info("Service - modifyCommunityPost : 끝");
		return new CommunityPostResponseDto(communityPost);
	}

	@Override
	public ApiResponseDto deleteCommunityPost(Long id) {
		log.info("Service - deleteCommunityPost : 시작");

		CommunityPost communityPost = findPost(id);

		// TODO : 작성자 확인 추가
		communityPostRepository.delete(communityPost);

		log.info("Service - deleteCommunityPost : 끝");
		return new ApiResponseDto("게시글 삭제 완료", HttpStatus.OK.value());
	}

	@Override
	public CommunityPost findPost(Long id) {
		return communityPostRepository.findById(id).orElseThrow(
				() -> new IllegalArgumentException("존재하지 않는 글입니다.")
		);
	}
}
