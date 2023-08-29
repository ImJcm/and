package com.sparta.and.controller;


import com.sparta.and.dto.chat.ChatMessageDto;
import com.sparta.and.service.StompChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
@RequiredArgsConstructor
@Slf4j(topic = "stompController")
public class StompChatController {
    private final StompChatService stompChatService;

    @MessageMapping("/api/chat/enter")
    public void enter(@RequestBody ChatMessageDto message) {
        System.out.println(message);
        stompChatService.enter(message);
    }

    @MessageMapping("/api/chat/message")
    public void message(@RequestBody ChatMessageDto message) {
        System.out.println(message);
        stompChatService.message(message);
    }
}
