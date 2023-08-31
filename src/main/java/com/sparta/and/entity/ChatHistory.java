package com.sparta.and.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@RedisHash(value = "chat_history")
public class ChatHistory implements Serializable {
    @Id
    private String id;
    private String chatId;
    private String chatContent;
    private String writer;
    private LocalDateTime createDateTime;

    @Builder
    public ChatHistory(String id, String chatId, String chatContent, String writer, LocalDateTime createDateTime) {
        this.id = id;
        this.chatId = chatId;
        this.chatContent = chatContent;
        this.writer = writer;
        this.createDateTime = createDateTime;
    }
}
