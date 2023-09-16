package com.sparta.and.dto.response;

import com.sparta.and.entity.Board;
import com.sparta.and.entity.Category;
import com.sparta.and.entity.MiddleCategory;

import lombok.Getter;

@Getter
public class BoardResponseDto {
	private Long id;
	private String title;
	private String contents;
	private MiddleCategory categoryId;
	private String categoryName;
	private Long noticeViews;

	public BoardResponseDto(Board board) {
		this.id= board.getId();
		this.title = board.getTitle();
		this.contents = board.getContents();
		this.categoryId = board.getCategoryId();
		this.categoryName = board.getCategoryId().getCategoryName();
		this.noticeViews = board.getNoticeViews();
	}
}
