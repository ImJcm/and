package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.chat.ChatMessageDto;
import com.sparta.and.dto.chat.ChatroomRequestDto;
import com.sparta.and.dto.chat.ChatroomResponseDto;
import com.sparta.and.entity.Chatroom;
import com.sparta.and.entity.TimeStamped;
import com.sparta.and.entity.User;
import com.sparta.and.repository.ChatroomRepository;
import com.sparta.and.repository.UserRepository;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatroomServiceImpl implements ChatroomService{
    private final UserRepository userRepository;
    private final ChatroomRepository chatroomRepository;
    private final ChatHistoryService chatHistoryService;

    @Override
    public List<ChatroomResponseDto> getChatRooms(UserDetailsImpl userDetails) {
        List<Chatroom> chatroomList = chatroomRepository.findAllByFounderOrParticipant(userDetails.getUser(), userDetails.getUser());

        return chatroomList.stream().map(ChatroomResponseDto::new).toList();
    }

    @Override
    public ChatroomResponseDto createChatroom(ChatroomRequestDto ChatroomRequestDto, UserDetailsImpl userDetails) {
        User participant = userRepository.findById(ChatroomRequestDto.getParticipant()).orElseThrow(() -> new IllegalArgumentException("해당되는 사용자가 없습니다."));

        Chatroom chatroom = Chatroom.builder()
                .chatroomName(ChatroomRequestDto.getChatroomName())
                .founder(userDetails.getUser())
                .participant(participant)
                .build();

        if(chatroomRepository.findByFounderAndParticipant(userDetails.getUser(), participant) != null) {
            throw new IllegalArgumentException("채팅방이 존재합니다.");
        }

        return new ChatroomResponseDto(chatroomRepository.save(chatroom));
    }

    @Override
    public ChatroomResponseDto getChatRoom(Long roomId, UserDetailsImpl userDetails) {
        Chatroom chatroom = chatroomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다."));

        return new ChatroomResponseDto(chatroom);
    }

    @Transactional
    @Override
    public ApiResponseDto deleteRoom(Long roomId, UserDetailsImpl userDetails) {
        Chatroom chatroom = chatroomRepository.findById(roomId).orElseThrow(() -> new IllegalArgumentException("채팅방이 존재하지 않습니다"));

        if(userDetails == null) {
            throw new IllegalArgumentException("로그인이 필요합니다.");
        }

        User user = userDetails.getUser();

        if(user.getUserId() == chatroom.getFounder().getUserId()) {
            chatroomRepository.delete(chatroom);
            chatHistoryService.deleteChatHistorys(roomId);
        } else if(user.getUserId() == chatroom.getParticipant().getUserId()){
            chatroom.setParticipant(null);

            ChatMessageDto chatMessageDto = new ChatMessageDto();
            chatMessageDto.setRoomId(String.valueOf(roomId));
            chatMessageDto.setMessage(user.getNickname() + "님이 나갔습니다.");
            chatMessageDto.setWriter(user.getUserName());
            chatMessageDto.setCreateDate(LocalDateTime.now().format(TimeStamped.FORMATTER));
            chatHistoryService.createChatHistory(chatMessageDto);
        }

        return new ApiResponseDto("채팅방 삭제 성공", HttpStatus.OK.value());
    }
}
