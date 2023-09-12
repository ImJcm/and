package com.sparta.and.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.and.entity.Post;
import com.sparta.and.entity.ReportPost;

public interface ReportPostRepository extends JpaRepository <ReportPost, Long> {
	ReportPost findByUserUserIdAndPostId(Long userId, Long postId);

}
