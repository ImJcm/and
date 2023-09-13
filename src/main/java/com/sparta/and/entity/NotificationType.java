package com.sparta.and.entity;

import lombok.Getter;

@Getter
public enum NotificationType {
    CHAT("채팅");

    private final String description;

    NotificationType(String description) {
        this.description = description;
    }
}
