package com.sparta.and.dto.chat;

import com.sparta.and.entity.ChatHistory;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
public class ChatHistoryDto {
    private Long id;
    private Long roomId;
    private String writer;
    private String message;
    private String messageType;
    private String sendDate;

    public ChatHistoryDto(ChatHistory chatHistory) {
        this.id = chatHistory.getId();
        this.roomId = chatHistory.getRoomId();
        this.writer = chatHistory.getWriter();
        this.message = chatHistory.getMessage();
        this.messageType = chatHistory.getMessageType();
        this.sendDate = chatHistory.getSendDate();
    }

}
