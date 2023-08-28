package com.sparta.and.service;

import com.sparta.and.dto.chat.ChatroomRequestDto;
import com.sparta.and.dto.chat.ChatroomResponseDto;
import com.sparta.and.entity.Chatroom;
import com.sparta.and.entity.User;
import com.sparta.and.repository.ChatroomRepository;
import com.sparta.and.repository.UserRepository;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatroomService {
    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;

    public List<ChatroomResponseDto> getChatRooms(UserDetailsImpl userDetails) {
        List<Chatroom> chatroomList = chatroomRepository.findAllByFounderOrParticipant(userDetails.getUser(), userDetails.getUser());

        return chatroomList.stream().map(ChatroomResponseDto::new).toList();
    }


    public ChatroomResponseDto createChatroom(ChatroomRequestDto ChatroomRequestDto, UserDetailsImpl userDetails) {
        User participant = userRepository.findById(ChatroomRequestDto.getParticipant()).orElseThrow(() -> new IllegalArgumentException("해당되는 사용자가 없습니다."));

        Chatroom chatroom = Chatroom.builder()
                .chatroomName(ChatroomRequestDto.getChatroomName())
                .founder(userDetails.getUser())
                .participant(participant)
                .build();

        // 동일한 chatroom이 있는지 검사하는 로직 필요

        chatroomRepository.save(chatroom);

        return new ChatroomResponseDto(chatroom);
    }


    public ChatroomResponseDto getChatRoom(Long roomId, UserDetailsImpl userDetails) {
        Chatroom chatroom = chatroomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        return new ChatroomResponseDto(chatroom);
    }
}
