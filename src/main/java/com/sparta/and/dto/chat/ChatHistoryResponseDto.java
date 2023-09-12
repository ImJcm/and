package com.sparta.and.dto.chat;

import com.sparta.and.entity.ChatHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ChatHistoryResponseDto {
    private Long id;
    private Long roomId;
    private String writer;
    private String message;
    private String messageType;
    private String sendDate;

    public ChatHistoryResponseDto(ChatHistory chatHistory) {
        this.id = chatHistory.getId();
        this.roomId = chatHistory.getChatroom().getId();
        this.writer = chatHistory.getWriter();
        this.message = chatHistory.getMessage();
        this.messageType = "message";
        this.sendDate = chatHistory.getSendDate();
    }

    // Enter 시, Type 변환을 위한 생성자
    public ChatHistoryResponseDto(ChatHistoryRequestDto chatHistoryRequestDto) {
        this.id = chatHistoryRequestDto.getId();
        this.roomId = chatHistoryRequestDto.getRoomId();
        this.writer = chatHistoryRequestDto.getWriter();
        this.message = chatHistoryRequestDto.getMessage();
        this.messageType = chatHistoryRequestDto.getMessageType();
        this.sendDate = chatHistoryRequestDto.getSendDate();
    }

    public ChatHistoryResponseDto(ChatHistoryDto chatHistoryDto) {
        this.id = chatHistoryDto.getId();
        this.roomId = chatHistoryDto.getRoomId();
        this.writer = chatHistoryDto.getWriter();
        this.message = chatHistoryDto.getMessage();
        this.messageType = chatHistoryDto.getMessageType();
        this.sendDate = chatHistoryDto.getSendDate();
    }
}
