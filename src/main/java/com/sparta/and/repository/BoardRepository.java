package com.sparta.and.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.and.entity.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
