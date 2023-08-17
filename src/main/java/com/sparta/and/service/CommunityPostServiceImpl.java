package com.sparta.and.service;

import com.sparta.and.dto.CommunityPostRequestDto;
import com.sparta.and.dto.CommunityPostResponseDto;
import com.sparta.and.entity.CommunityPost;
import com.sparta.and.repository.CommunityPostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@Slf4j(topic = "CommunityPostService")
@RequiredArgsConstructor
public class CommunityPostServiceImpl implements CommunityPostService {

	private final CommunityPostRepository communityPostRepository;

	@Override
	public CommunityPostResponseDto createCommunityPost(CommunityPostRequestDto requestDto) {
		log.info("Service - createCommunityPost");
		CommunityPost communityPost = communityPostRepository.save(new CommunityPost(requestDto));
		return new CommunityPostResponseDto(communityPost);
	}
}
