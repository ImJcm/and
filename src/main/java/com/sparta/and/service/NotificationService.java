package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.response.NotificationResponseDto;
import com.sparta.and.entity.ChatHistory;
import com.sparta.and.entity.Notification;
import com.sparta.and.entity.NotificationType;
import com.sparta.and.entity.User;
import com.sparta.and.security.UserDetailsImpl;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;

public interface NotificationService {
    SseEmitter sub(Long userId, String lastEventId);

    void sendToClient(SseEmitter emitter, String SseId, Object data);

    void send(User receiver, NotificationType notificationType, String content, String url);

    Notification createNotification(User receiver, NotificationType notificationType, String content, String url);

    ApiResponseDto deleteNotification(Long noticeId, User user);

    ApiResponseDto readNotification(Long noticeId, User user);

    List<NotificationResponseDto> getNotifications(UserDetailsImpl userDetails);
}
