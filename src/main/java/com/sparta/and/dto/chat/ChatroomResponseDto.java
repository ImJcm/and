package com.sparta.and.dto.chat;

import com.sparta.and.entity.Chatroom;
import com.sparta.and.entity.TimeStamped;
import lombok.Getter;

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
        this.createdDate = chatroom.getCreatedDateFormatted(TimeStamped.FORMATTER_DATE);
        if(chatroom.getFounder() == null) {
            this.founder = 0L;
            this.founder_nickname = "알수없는 사용자";
        } else {
            this.founder = chatroom.getFounder().getUserId();
            this.founder_nickname = chatroom.getFounder().getNickname();
        }

        if(chatroom.getParticipant() == null) {
            this.participant = 0L;
            this.participant_nickname = "알수없는 사용자";
        } else {
            this.participant = chatroom.getParticipant().getUserId();
            this.participant_nickname = chatroom.getParticipant().getNickname();
        }


    }
}
