package com.sparta.and.entity;

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

	@Column(name = "author", nullable = false)
	private String author;

	@Column(name = "title", nullable = false)
	private String title;

	@Column(name = "company", nullable = false)
	private String company;

	@Column(name = "deadline", nullable = false)
	private String deadline;

	@Column(name = "homepage", nullable = false)
	private String homepage;

	@Column(name = "postViews", nullable = false)
	private Long contestBoardViews;

	@Column(name = "contents", nullable = false)
	private String contents;
}
