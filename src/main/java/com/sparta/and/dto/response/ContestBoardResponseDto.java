package com.sparta.and.dto.response;

import com.sparta.and.entity.ContestBoard;

import lombok.Getter;

@Getter
public class ContestBoardResponseDto {

	private Long id;
	private String author;
	private String title;
	private String contents;
	private Long bookmarkCnt;

	public ContestBoardResponseDto (ContestBoard contestBoard){
		this.id = contestBoard.getId();
		this.author = contestBoard.getAuthor();
		this.title = contestBoard.getTitle();
		this.contents = contestBoard.getContents();
		this.bookmarkCnt = (long)contestBoard.getBookmarkList().size();
	}

}
