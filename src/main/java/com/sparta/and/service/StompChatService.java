package com.sparta.and.service;

import com.sparta.and.dto.chat.ChatMessageDto;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StompChatService {
    private final SimpMessagingTemplate template;

    public void enter(ChatMessageDto message, UserDetailsImpl userDetails) {

        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }

    public void message(ChatMessageDto message, UserDetailsImpl userDetails) {
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }
}
