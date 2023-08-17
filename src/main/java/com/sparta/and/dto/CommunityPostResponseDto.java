package com.sparta.and.dto;

import com.sparta.and.entity.CommunityPost;
import lombok.Getter;

@Getter
public class CommunityPostResponseDto {
	private String title;
	private String contents;

	public CommunityPostResponseDto(CommunityPost communityPost) {
		this.title = communityPost.getTitle();
		this.contents = communityPost.getContents();
	}
}
