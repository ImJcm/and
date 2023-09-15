package com.sparta.and.controller;

import com.sparta.and.dto.chat.ChatHistoryRequestDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.StompChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
public class StompChatController {
    private final StompChatService stompChatService;

    @MessageMapping("/api/chat/enter")
    public void enter(@RequestBody ChatHistoryRequestDto message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        stompChatService.enter(message);
    }

    @MessageMapping("/api/chat/message")
    public void message(@RequestBody ChatHistoryRequestDto message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        stompChatService.message(message);
    }
}
