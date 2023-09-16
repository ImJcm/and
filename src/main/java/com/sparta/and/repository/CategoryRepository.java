package com.sparta.and.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sparta.and.entity.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {

}
