package com.sparta.and.dto.chat;

import com.sparta.and.entity.Chatroom;
import lombok.Getter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;

@Getter
public class ChatroomResponseDto {
    private Long roomId;
    private String chatroomName;
    private Long founder;
    private Long participant;
    private Set<WebSocketSession> sessions = new HashSet<>();

    public ChatroomResponseDto(String chatroomName, Long founderId, Long participantId) {
        this.chatroomName = chatroomName;
        this.founder = founderId;
        this.participant = participantId;
    }

    public ChatroomResponseDto(Chatroom chatroom) {
        this.roomId = chatroom.getId();
        this.chatroomName = chatroom.getChatroomName();
        this.founder = chatroom.getFounder().getUserId();
        this.participant = chatroom.getParticipant().getUserId();
    }
}
