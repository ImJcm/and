package com.sparta.and.repository;

import java.util.Arrays;
import java.util.List;

import com.sparta.and.entity.ContestBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContestBoardRepository extends JpaRepository<ContestBoard, Long> {
	List <ContestBoard> findAllByOrderByCreatedDateDesc();
}
