package com.sparta.and.controller;


import com.sparta.and.dto.chat.ChatMessageDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.StompChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api/chat")
public class StompChatController {
    private final StompChatService stompChatService;
    //private final SimpMessagingTemplate template;

    @MessageMapping("/enter")
    public void enter(@RequestBody ChatMessageDto message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        stompChatService.enter(message, userDetails);
        //message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        //template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }

    @MessageMapping("/message")
    public void message(@RequestBody ChatMessageDto message, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        stompChatService.message(message, userDetails);
        //template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }
}
