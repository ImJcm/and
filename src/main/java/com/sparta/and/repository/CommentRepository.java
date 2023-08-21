package com.sparta.and.repository;

import com.sparta.and.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long>, CommentRepositoryQuery {
}
