package com.sparta.and.dto.chat;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.sparta.and.entity.ChatHistory;
import com.sparta.and.entity.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatHistoryRequestDto {
    private Long id;
    private Long roomId;
    private String writer;
    private String message;
    /**
     * "enter" : 입장 시
     * "message" : 메시지 전송 시
     */
    private String messageType;
    private String sendDate;

    public ChatHistoryRequestDto(ChatHistory chatHistory) {
        this.id = chatHistory.getId();
        this.roomId = chatHistory.getRoomId();
        this.writer = chatHistory.getWriter();
        this.message = chatHistory.getMessage();
        this.messageType = chatHistory.getMessageType();
        this.sendDate = chatHistory.getSendDate();
    }
}
