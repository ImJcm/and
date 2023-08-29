package com.sparta.and.service;

import com.sparta.and.dto.chat.ChatMessageDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@RequiredArgsConstructor
public class StompChatService {
    private final SimpMessagingTemplate template;

    public void enter(@RequestBody ChatMessageDto message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }

    public void message(@RequestBody ChatMessageDto message) {
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }
}
