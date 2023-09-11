package com.sparta.and.repository;

import com.sparta.and.entity.UserBlackList;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserBlackListRepository extends JpaRepository<UserBlackList, Long> {


    Optional<UserBlackList> findByUsername(String username);
}
