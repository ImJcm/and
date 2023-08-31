package com.sparta.and.repository;

import com.sparta.and.entity.ChatHistory;
import org.springframework.data.repository.CrudRepository;

public interface ChatHistoryRepository extends CrudRepository<ChatHistory, String> {
}
