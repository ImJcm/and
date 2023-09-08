package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.chat.ChatHistoryResponseDto;
import com.sparta.and.dto.chat.ChatHistoryRequestDto;
import com.sparta.and.entity.ChatHistory;

import java.util.List;

public interface ChatHistoryService {
    /**
     * 채팅 내역 생성
     *
     * @param message       클라이언트로부터 받은 채팅 정보
     * @return              ChatHistory 객체
     */
    ChatHistory createChatHistory(ChatHistoryRequestDto message);

    /**
     * 채팅방의 모든 채팅 내역 조회
     *
     * @param roomId        채팅방의 id
     * @return              List 채팅내역
     */
    List<ChatHistoryResponseDto> getChatHistorys(Long roomId);

    /**
     * 채팅방의 채팅내역 모두 삭제
     *
     * @param roomId        채팅방의 id
     * @return              채팅 삭제 결과 반환
     */
    ApiResponseDto deleteChatHistorys(Long roomId);

    /**
     * ChatHistoryRequestDto -> ChatHistoryResponseDto
     *
     * @param message       보낸 채팅
     * @return              ResponseDto 변환 결과
     */
    ChatHistoryResponseDto updateRequestToResponseDto(ChatHistoryRequestDto message);
}
