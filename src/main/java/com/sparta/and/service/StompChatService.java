package com.sparta.and.service;

import com.sparta.and.dto.chat.ChatMessageDto;
import com.sparta.and.entity.ChatHistory;
import com.sparta.and.entity.TimeStamped;
import com.sparta.and.repository.UserRepository;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.concurrent.RejectedExecutionException;

@Service
@RequiredArgsConstructor
public class StompChatService {
    private final SimpMessagingTemplate template;
    private final ChatHistoryServiceImpl chathistoryServiceImpl;

    public void enter(ChatMessageDto message) {
        message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
        message.setCreateDate(LocalDateTime.now().format(TimeStamped.FORMATTER_DATE_HOUR_MINUTE));
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }

    public void message(ChatMessageDto message) {
        ChatHistory chatHistory = chathistoryServiceImpl.createChatHistory(message);

        message.setCreateDate(chatHistory.getCreateDateTime().format(TimeStamped.FORMATTER_DATE_HOUR_MINUTE));
        template.convertAndSend("/sub/api/chat/room/" + message.getRoomId(), message);
    }

}
