package com.sparta.and.repository;

import java.util.List;

import com.sparta.and.entity.ContestPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestPostRepository extends JpaRepository<ContestPost, Long> {
	List <ContestPost> findAllByOrderByCreatedDateDesc();
}
