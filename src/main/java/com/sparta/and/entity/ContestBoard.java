package com.sparta.and.entity;

import java.util.ArrayList;
import java.util.List;

import com.sparta.and.dto.request.ContestBoardRequestDto;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "contest_boards")
@NoArgsConstructor
public class ContestBoard extends TimeStamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "contest_board_id")
	private Long id;

	@Column(name = "author")
	private String author;

	@Column(name = "title")
	private String title;

	@Column(name = "company")
	private String company;

	@Column(name = "deadline")
	private String deadline;

	@Column(name = "homepage")
	private String homepage;

	@Column(name = "postViews")
	private Long contestBoardViews;

	@Column(name = "contents")
	private String contents;

	// 북마크 카운트
	@Column
	private Long bookmarkCnt;

	// 북마크
	@OneToMany(mappedBy = "contestBoard", cascade = CascadeType.REMOVE)
	private List<Bookmark> bookmarkList = new ArrayList<>();

	public ContestBoard(ContestBoardRequestDto requestDto) {
		this.author = requestDto.getAuthor();
		this.title = requestDto.getTitle();
		this.contents = requestDto.getContents();
	}

	public void setBookmarkCnt(Long bookmarkCnt){
		this.bookmarkCnt = bookmarkCnt;
	}
}
