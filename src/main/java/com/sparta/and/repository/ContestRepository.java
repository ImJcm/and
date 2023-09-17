package com.sparta.and.repository;

import com.sparta.and.dto.SearchRequestDto;
import com.sparta.and.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContestRepository extends JpaRepository<Contest, Long> {
}
