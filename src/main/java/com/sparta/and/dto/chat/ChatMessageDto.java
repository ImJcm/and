package com.sparta.and.dto.chat;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ChatMessageDto {
    private String roomId;
    private String writer;
    private String message;
    private String createDate;
}
