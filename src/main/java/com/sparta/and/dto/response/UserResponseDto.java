package com.sparta.and.dto.response;

import com.sparta.and.entity.User;
import lombok.Getter;

@Getter
public class UserResponseDto {
    private Long userId;
    private String username;
    private String nickname;

    public UserResponseDto(User user) {
        this.userId = user.getUserId();
        this.username = user.getUserName();
        this.nickname = user.getNickname();
    }
}
