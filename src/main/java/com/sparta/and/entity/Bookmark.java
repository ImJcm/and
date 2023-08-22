package com.sparta.and.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "bookmarks")
public class Bookmark {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "contest_board_id", nullable = false)
	private ContestPost contestBoard;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User userId;

	public Bookmark(ContestPost contestBoard) {
		this.contestBoard = contestBoard;
	}
}
