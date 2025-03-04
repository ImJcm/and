package com.sparta.and.repository;

import java.util.Optional;

import com.sparta.and.entity.Comment;
import com.sparta.and.entity.ReportComment;
import com.sparta.and.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportCommentRepository extends JpaRepository<ReportComment, Long> {
	Optional<ReportComment> findByUserAndComment(User connectUser, Comment comment);
}
