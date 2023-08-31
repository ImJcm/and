package com.sparta.and.dto.chat;

import com.sparta.and.entity.ChatHistory;
import com.sparta.and.entity.TimeStamped;
import lombok.Getter;

@Getter
public class ChatHistoryResponseDto {
    private String chatId;
    private String writer;
    private String message;
    private String createDate;

    public ChatHistoryResponseDto(ChatHistory chatHistory) {
        this.chatId = chatHistory.getChatId();
        this.writer = chatHistory.getWriter();
        this.message = chatHistory.getChatContent();
        this.createDate = chatHistory.getCreateDateTime().format(TimeStamped.FORMATTER);
    }
}
