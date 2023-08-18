package com.sparta.and.dto.response;

import com.sparta.and.entity.Post;
import lombok.Getter;

@Getter
public class PostResponseDto {
	private String title;
	private String contents;
	private Long communityPostViews;

	public PostResponseDto(Post post) {
		this.title = post.getTitle();
		this.contents = post.getContents();
		this.communityPostViews = post.getPostviews();
	}
}
