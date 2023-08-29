package com.sparta.and.repository;

import com.sparta.and.entity.Chatroom;
import com.sparta.and.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatroomRepository extends JpaRepository<Chatroom, Long> {
    List<Chatroom> findAllByFounderOrParticipant(User founder, User participant);
}
