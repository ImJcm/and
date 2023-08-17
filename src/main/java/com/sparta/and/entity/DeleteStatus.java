package com.sparta.and.entity;

import lombok.Getter;

@Getter
public enum DeleteStatus {
    Y("삭제 o"),
    N("삭제 x");

    private final String description;

    DeleteStatus(String description){
        this.description = description;
    }
}
