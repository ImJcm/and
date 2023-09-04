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
    /**
     * "enter" : 입장 시
     * "message" : 메시지 전송 시
     */
    private String messageType;
    private String createDate;
}
