package com.sparta.and.dto.response;

import com.sparta.and.entity.CommunityPost;
import lombok.Getter;

@Getter
public class CommunityPostResponseDto {
	private String title;
	private String contents;
	private Long communityPostViews;

	public CommunityPostResponseDto(CommunityPost communityPost) {
		this.title = communityPost.getTitle();
		this.contents = communityPost.getContents();
		this.communityPostViews = communityPost.getCommunityPostViews();
	}
}
