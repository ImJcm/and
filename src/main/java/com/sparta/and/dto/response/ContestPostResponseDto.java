package com.sparta.and.dto.response;

import com.sparta.and.entity.ContestPost;

import lombok.Getter;

@Getter
public class ContestPostResponseDto {

	private Long id;
	private String author;
	private String title;
	private String contents;
	private Long bookmarkCnt;

	public ContestPostResponseDto(ContestPost contestBoard){
		this.id = contestBoard.getId();
		this.author = contestBoard.getAuthor();
		this.title = contestBoard.getTitle();
		this.contents = contestBoard.getContents();
		this.bookmarkCnt = (long)contestBoard.getBookmarkList().size();
	}

}
