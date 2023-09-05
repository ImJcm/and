package com.sparta.and.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.io.Serializable;
import java.time.LocalDateTime;


@Entity
@Table(name = "chat_histories")
@Getter
@NoArgsConstructor
public class ChatHistory extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_history_id")
    private Long id;

    @Column(name = "room_id")
    private Long roomId;

    @Column(name = "writer")
    private String writer;

    @Column(name = "message")
    private String message;

    @Column(name = "message_type")
    private String messageType;

    @Column(name = "send_date")
    private String sendDate;

    @ManyToOne
    @JoinColumn(name = "chatroom_id")
    private Chatroom chatroom;

    @Builder
    public ChatHistory(Long chatHistoryId, Long roomId, Chatroom chatroom, String message, String messageType, String writer, String sendDate) {
        this.id = chatHistoryId;
        this.roomId = roomId;
        this.chatroom = chatroom;
        this.message = message;
        this.messageType = messageType;
        this.writer = writer;
        this.sendDate = sendDate;
    }
}
