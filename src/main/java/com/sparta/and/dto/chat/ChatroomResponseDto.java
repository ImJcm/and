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

    private String founder_nickname;
    private Long participant;

    private String participant_nickname;
    private String createdDate;

    public ChatroomResponseDto(Chatroom chatroom) {
        this.roomId = chatroom.getId();
        this.chatroomName = chatroom.getChatroomName();
        this.founder = chatroom.getFounder().getUserId();
        this.founder_nickname = chatroom.getFounder().getNickname();
        this.participant = chatroom.getParticipant().getUserId();
        this.participant_nickname = chatroom.getParticipant().getNickname();
        this.createdDate = chatroom.getCreatedDateFormatted();
    }
}
