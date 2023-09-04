package com.sparta.and.dto.response;

import com.sparta.and.entity.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserSearchResponseDto {
    private Long userId;
    private String nickname;
    private String username;

    public UserSearchResponseDto(User user) {
        this.userId = user.getUserId();
        this.nickname = user.getNickname();
        this.username = user.getUserName();
    }
}
