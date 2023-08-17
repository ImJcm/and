package com.sparta.and.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class KakaoUserInfoDto {
    private Long id;
    private String username;
    public KakaoUserInfoDto(Long id, String username) {
        this.id = id;
        this.username = username;
    }
}
