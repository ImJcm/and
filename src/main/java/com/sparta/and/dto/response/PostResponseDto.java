package com.sparta.and.dto.response;

import com.sparta.and.entity.Post;
import com.sparta.and.entity.TimeStamped;
import lombok.Getter;

@Getter
public class PostResponseDto {
	private Long postId;
	private String title;
	private String contents;
	private String writer;
	private Long communityPostViews;
	private String modifiedDate;

	public PostResponseDto(Post post) {
		this.postId = post.getId();
		this.title = post.getTitle();
		this.writer = post.getUser().getNickname();
		this.contents = post.getContents();
		this.modifiedDate = post.getModifiedDateFormatted(TimeStamped.FORMATTER_DATE_HOUR_MINUTE);
		this.communityPostViews = post.getPostviews();
	}
}
