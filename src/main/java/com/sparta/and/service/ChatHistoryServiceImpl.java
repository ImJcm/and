package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.chat.ChatHistoryResponseDto;
import com.sparta.and.dto.chat.ChatMessageDto;
import com.sparta.and.entity.ChatHistory;
import com.sparta.and.repository.ChatHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChatHistoryServiceImpl implements ChatHistoryService{
    private final RedisTemplate<String, ChatHistory> redisTemplate;
    private final ChatHistoryRepository chatHistoryRepository;

    @Override
    public ChatHistory createChatHistory(ChatMessageDto message) {
        List<ChatHistory> chatHistoryList = redisTemplate.opsForList().range(message.getRoomId(),0,-1);

        String chatId = chatHistoryList.size() == 0 ? String.valueOf(1L) :
                String.valueOf(Long.parseLong(chatHistoryList.get(chatHistoryList.size()-1).getChatId()) + 1);

        ChatHistory chatHistory = ChatHistory.builder()
                .id(message.getRoomId())
                .chatId(chatId)
                .chatContent(message.getMessage())
                .writer(message.getWriter())
                .createDateTime(LocalDateTime.now())
                .build();

        redisTemplate.opsForList().rightPush(message.getRoomId(), chatHistory);

        //채팅방에 대한 채팅내역들의 기한을 설정 = 60일, 채팅이 생성될 때 기한 초기화
        redisTemplate.expire(message.getRoomId(), 60, TimeUnit.DAYS);

        return chatHistory;
    }

    @Override
    public List<ChatHistoryResponseDto> getChatHistorys(Long roomId) {
        List<ChatHistory> chatHistoryList = redisTemplate.opsForList().range(String.valueOf(roomId),0,-1);

        return chatHistoryList.stream().map(ChatHistoryResponseDto::new).toList();
    }

    @Override
    public ApiResponseDto deleteChatHistorys(Long roomId) {
        redisTemplate.delete(String.valueOf(roomId));

        return new ApiResponseDto("채팅 내역 삭제 성공", HttpStatus.OK.value());
    }
}

