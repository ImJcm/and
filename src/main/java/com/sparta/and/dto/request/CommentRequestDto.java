package com.sparta.and.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentRequestDto {
	private Long parentId;
	private String content;

	public CommentRequestDto(String content) {
		this.content = content;
	}
}
