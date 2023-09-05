package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.chat.ChatHistoryDto;
import com.sparta.and.dto.chat.ChatHistoryResponseDto;
import com.sparta.and.dto.chat.ChatHistoryRequestDto;
import com.sparta.and.entity.ChatHistory;
import com.sparta.and.entity.Chatroom;
import com.sparta.and.entity.TimeStamped;
import com.sparta.and.repository.ChatHistoryRepository;
import com.sparta.and.repository.ChatroomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ChatHistoryServiceImpl implements ChatHistoryService{
    private final RedisTemplate<String, ChatHistoryDto> redisTemplateChat;
    private final ChatHistoryRepository chatHistoryRepository;
    private final ChatroomRepository chatroomRepository;

    @Override
    public ChatHistory createChatHistory(ChatHistoryRequestDto chatHistoryRequestDto) {
        Chatroom chatroom = chatroomRepository.findById(chatHistoryRequestDto.getRoomId()).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 채팅방입니다."));
        // MySql 저장
        ChatHistory chatHistory = ChatHistory.builder()
                .chatroom(chatroom)
                .roomId(chatroom.getId())
                .message(chatHistoryRequestDto.getMessage())
                .messageType(chatHistoryRequestDto.getMessageType())
                .writer(chatHistoryRequestDto.getWriter())
                .sendDate(LocalDateTime.now().format(TimeStamped.FORMATTER_DATE_HOUR_MINUTE))
                .build();
        ChatHistory chat = chatHistoryRepository.save(chatHistory);

        // Redis DB 저장
        redisTemplateChat.opsForList().rightPush(String.valueOf(chatHistoryRequestDto.getRoomId()), new ChatHistoryDto(chat));

        // 채팅방을 Key로 채팅내역들의 만료기간을 1 MIN으로 expire 설정
        redisTemplateChat.expire(String.valueOf(chatHistoryRequestDto.getRoomId()), 1, TimeUnit.MINUTES);

        return chat;
    }

    @Override
    public List<ChatHistoryResponseDto> getChatHistorys(Long roomId) {
        List<ChatHistoryResponseDto> chatHistoryList = new ArrayList<>();
        // Redis에서 100개의 채팅내역 불러오기
        List<ChatHistoryDto> chatHistoryRedisList = redisTemplateChat.opsForList().range(String.valueOf(roomId),0,99);

        if(chatHistoryRedisList == null || chatHistoryRedisList.isEmpty()) {
            List<ChatHistory> dbChatHistoryList = chatHistoryRepository.findTop100ByChatroomIdOrderByCreatedDateAsc(roomId);

            dbChatHistoryList.stream().forEach((chat) -> {
                ChatHistoryResponseDto chatHistoryResponseDto = new ChatHistoryResponseDto(chat);
                chatHistoryList.add(chatHistoryResponseDto);
                redisTemplateChat.opsForList().rightPush(String.valueOf(roomId), new ChatHistoryDto(chat));
            });
        } else {
            chatHistoryList.addAll(chatHistoryRedisList.stream().map(ChatHistoryResponseDto::new).toList());
        }

        return chatHistoryList;
    }

    @Override
    public ApiResponseDto deleteChatHistorys(Long roomId) {
        redisTemplateChat.delete(String.valueOf(roomId));

        return new ApiResponseDto("채팅 내역 삭제 성공", HttpStatus.OK.value());
    }

    @Override
    public ChatHistoryResponseDto updateRequestToResponseDto(ChatHistoryRequestDto message) {
        message.setSendDate(LocalDateTime.now().format(TimeStamped.FORMATTER_DATE_HOUR_MINUTE));
        ChatHistoryResponseDto newMessage = new ChatHistoryResponseDto(message);
        if(message.getMessageType().equals("enter")) {
            newMessage.setMessage(message.getWriter() + "님이 입장하셨습니다.");
        }
        return newMessage;
    }
}

