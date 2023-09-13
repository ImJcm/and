package com.sparta.and.controller;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.response.NotificationResponseDto;
import com.sparta.and.security.UserDetailsImpl;
import com.sparta.and.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {
    private final NotificationService notificationService;

    @GetMapping(value = "/sub", produces = "text/event-stream")
    public SseEmitter subscribe(@AuthenticationPrincipal UserDetailsImpl userDetails,
                                @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
        return notificationService.sub(userDetails.getUser().getUserId(),lastEventId);
    }

    @GetMapping
    public ResponseEntity<List<NotificationResponseDto>> getNotifications(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(notificationService.getNotifications(userDetails));
    }

    @PostMapping("/{notificationId}")
    public ResponseEntity<ApiResponseDto> readNotification(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(notificationService.readNotification(notificationId, userDetails.getUser()));
    }

    @DeleteMapping("/{notificationId}")
    public ResponseEntity<ApiResponseDto> deleteNotification(@PathVariable Long notificationId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseEntity.ok().body(notificationService.deleteNotification(notificationId, userDetails.getUser()));
    }
}
