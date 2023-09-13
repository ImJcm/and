package com.sparta.and.service;

import com.sparta.and.dto.chat.ChatHistoryRequestDto;
import com.sparta.and.dto.chat.ChatHistoryResponseDto;
import com.sparta.and.entity.ChatHistory;
import com.sparta.and.entity.TimeStamped;
import com.sparta.and.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StompChatService {
    private final SimpMessagingTemplate template;
    private final ChatHistoryService chathistoryService;

    public void enter(ChatHistoryRequestDto message) {
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), chathistoryService.updateEnterRequestToResponseDto(message));
    }

    public void message(ChatHistoryRequestDto message) {
        ChatHistory chatHistory = chathistoryService.createChatHistory(message);
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), chathistoryService.updateMessageChatHistoryToResponseDto(chatHistory));
    }
}
