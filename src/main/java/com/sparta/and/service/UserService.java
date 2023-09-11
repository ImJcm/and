package com.sparta.and.service;

import com.sparta.and.dto.response.UserResponseDto;
import com.sparta.and.dto.response.UserSearchResponseDto;
import com.sparta.and.entity.User;
import com.sparta.and.repository.UserRepository;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;

    // 유저 조회
    public UserResponseDto getUser(UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getUser().getUserId()).orElseThrow(() ->
            new RejectedExecutionException("사용자가 없습니다."));

        return new UserResponseDto(user);
    }

    // 유저 검색
    public List<UserSearchResponseDto> searchUsers(String keyword) {
        return userRepository.findAllByUserNameContaining(keyword).stream().map(UserSearchResponseDto::new).toList();
    }
}
