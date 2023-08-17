package com.sparta.and.repository;

import com.sparta.and.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category,Long> {

    List<Category> findAllByCategoryId(Long id);

}
