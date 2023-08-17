package com.sparta.and.service;

import com.sparta.and.dto.CommunityPostRequestDto;
import com.sparta.and.dto.CommunityPostResponseDto;

public interface CommunityPostService {

	/**
	 * @param requestDto
	 * //	 * @param userDetails
	 * @return
	 */
	CommunityPostResponseDto createCommunityPost(CommunityPostRequestDto requestDto);
}
