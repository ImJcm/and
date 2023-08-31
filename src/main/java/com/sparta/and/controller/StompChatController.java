package com.sparta.and.controller;

import com.sparta.and.dto.chat.ChatMessageDto;
import com.sparta.and.service.StompChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final StompChatService stompChatService;

    @MessageMapping("/api/chat/enter")
    public void enter(@RequestBody ChatMessageDto message) {
        stompChatService.enter(message);
    }

    @MessageMapping("/api/chat/message")
    public void message(@RequestBody ChatMessageDto message) {
        stompChatService.message(message);
    }
}
