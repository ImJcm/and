package com.sparta.and.service;

import com.sparta.and.dto.CommunityPostRequestDto;
import com.sparta.and.dto.CommunityPostResponseDto;
import com.sparta.and.entity.CommunityPost;
import com.sparta.and.security.UserDetailsImpl;

public interface CommunityPostService {

	/**
	 *
	 * @param requestDto
	 * @param userDetails
	 * @return
	 */
	CommunityPostResponseDto createCommunityPost(CommunityPostRequestDto requestDto, UserDetailsImpl userDetails);

	CommunityPostResponseDto modifyCommunityPost(Long id, CommunityPostRequestDto requestDto);

	CommunityPost findPost(Long id);
}
