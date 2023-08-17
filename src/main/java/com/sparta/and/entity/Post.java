package com.sparta.and.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@Table(name = "post")
@NoArgsConstructor
public class Post extends Timestamped {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long postId;

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
	private Long postViews;

	@Column(name = "contents", nullable = false)
	private String contents;
}
