package com.sparta.and.repository;

import java.util.List;

import com.sparta.and.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestRepository extends JpaRepository<Contest, Long> {
	List<Contest> findAllByOrderByCreatedDateDesc();

	List<Contest> findByTitleContaining(String keyword);
}
