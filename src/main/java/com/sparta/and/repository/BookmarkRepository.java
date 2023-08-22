package com.sparta.and.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.and.entity.Bookmark;

public interface BookmarkRepository extends JpaRepository <Bookmark, Long> {
	Bookmark findByContestBoardId(Long id);
}
