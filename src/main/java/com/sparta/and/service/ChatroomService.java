package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.chat.ChatroomRequestDto;
import com.sparta.and.dto.chat.ChatroomResponseDto;
import com.sparta.and.entity.User;
import com.sparta.and.security.UserDetailsImpl;

import java.util.List;

public interface ChatroomService {
    /**
     * 현재 사용자의 모든 채팅방 목록 조회
     *
     * @param userDetails           현재 접속한 사용자
     * @return                      모든 채팅방 목록
     */
    List<ChatroomResponseDto> getChatRooms(UserDetailsImpl userDetails);

    /**
     * 채팅방 생성
     *
     * @param ChatroomRequestDto    채팅방 정보(채팅방 이름, 채팅 참여자)
     * @param userDetails           현재 사용자
     * @return                      생성한 채팅방 정보
     */
    ChatroomResponseDto createChatroom(ChatroomRequestDto ChatroomRequestDto, UserDetailsImpl userDetails);

    /**
     * 채팅방 상세 조회
     *
     * @param roomId                채팅방 id
     * @param userDetails           현재 접속한 사용자
     * @return                      채팅방 상세 정보
     */
    ChatroomResponseDto getChatRoom(Long roomId, UserDetailsImpl userDetails);

    /**
     * 채팅방 삭제
     *
     * @param roomId                채팅방 id
     * @param userDetails           현재 접속한 사용자
     * @return                      삭제 결과
     */
    ApiResponseDto deleteRoom(Long roomId, UserDetailsImpl userDetails);
}
