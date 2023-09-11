package com.sparta.and.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.and.entity.Bookmark;
import org.springframework.stereotype.Repository;

public interface BookmarkRepository extends JpaRepository <Bookmark, Long> {
	Bookmark findByContestId(Long id);
}
