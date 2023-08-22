package com.sparta.and.dto.request;

import lombok.Getter;

@Getter
public class CommentReportRequestDto {
    private Long commentId;
    private String reportReason;

}
