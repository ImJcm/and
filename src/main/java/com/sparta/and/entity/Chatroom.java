package com.sparta.and.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "chatroom")
public class Chatroom extends TimeStamped {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chatroom_id")
    private Long id;

    @Column
    private String chatroomName;

    @ManyToOne
    @JoinColumn(name = "founder_id")
    private User founder;

    @ManyToOne
    @JoinColumn(name = "participant_id")
    private User participant;

    @Builder
    public Chatroom(String chatroomName, User founder, User participant) {
        this.chatroomName = chatroomName;
        this.founder = founder;
        this.participant = participant;
    }

    public void setParticipant(User user) {
        this.participant = user;
    }
}
