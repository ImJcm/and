package com.sparta.and.repository;

import com.sparta.and.entity.ChatHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatHistoryRepository extends JpaRepository<ChatHistory, String> {
    List<ChatHistory> findTop100ByChatroomIdOrderByCreatedDateAsc(Long roomId);
}
