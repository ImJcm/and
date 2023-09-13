package com.sparta.and.service;

import com.sparta.and.dto.ApiResponseDto;
import com.sparta.and.dto.response.NotificationResponseDto;
import com.sparta.and.entity.Notification;
import com.sparta.and.entity.NotificationType;
import com.sparta.and.entity.User;
import com.sparta.and.repository.EmitterRepository;
import com.sparta.and.repository.NotificationRepository;
import com.sparta.and.security.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    // 60L * 1000 = 1분 / 60L * 1000 * 60 = 1시간
    private static final Long DEFAULT_TIMEOUT = 60L * 1000 * 60;
    private final EmitterRepository emitterRepository;
    private final NotificationRepository notificationRepository;

    @Transactional
    @Override
    public SseEmitter sub(Long userId, String lastEventId) {
        String sseId = userId + "_" + System.currentTimeMillis();

        SseEmitter emitter = emitterRepository.save(sseId, new SseEmitter(DEFAULT_TIMEOUT));

        emitter.onCompletion(() -> emitterRepository.deleteById(sseId));
        emitter.onTimeout(() -> emitterRepository.deleteById(sseId));
        emitter.onError((e) -> emitterRepository.deleteById(sseId));

        sendToClient(emitter, sseId, "EventStream Created. [userId=" + userId + "]");

        if(!lastEventId.isEmpty()) {
            Map<String, Object> events = emitterRepository.findAllEventCacheStartWithByUserId(String.valueOf(userId));
            events.entrySet().stream()
                    .filter(entry -> lastEventId.compareTo(entry.getKey()) < 0)
                    .forEach(entry -> sendToClient(emitter, entry.getKey(), entry.getValue()));
        }

        return emitter;
    }

    @Override
    public void sendToClient(SseEmitter emitter, String SseId, Object data) {
        try {
            emitter.send(SseEmitter.event()
                    .id(SseId)
                    .name("sse")
                    .data(data));
        } catch (IOException exception) {
            emitterRepository.deleteById(SseId);
            notificationRepository.deleteById(((NotificationResponseDto)data).getNotificationId());
            throw new RuntimeException("연결 오류!");
        }
    }

    @Override
    public void send(User receiver, NotificationType notificationType, String content, String url) {
        Notification notification = notificationRepository.save(createNotification(receiver, notificationType, content, url));
        String userId = String.valueOf(receiver.getUserId());

        Map<String, SseEmitter> sseEmitters = emitterRepository.findAllEmitterStartWithByUserId(userId);
        sseEmitters.forEach(
                (key,emitter) -> {
                    emitterRepository.saveEventCache(key, notification);
                    sendToClient(emitter, key, new NotificationResponseDto(notification));
                }
        );
    }

    @Override
    public Notification createNotification(User receiver, NotificationType notificationType, String content, String url) {
        return Notification.builder()
                .receiver(receiver)
                .content(content)
                .notificationType(notificationType)
                .url(url)
                .isRead(false)
                .build();
    }

    @Transactional
    @Override
    public ApiResponseDto readNotification(Long noticeId, User user) {
        Notification notification = notificationRepository.findById(noticeId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다."));

        if(!notification.getIsRead()) {
            notification.setIsRead(true);
        }
        return new ApiResponseDto("알림 읽음 성공", HttpStatus.OK.value());
    }

    @Transactional
    @Override
    public ApiResponseDto deleteNotification(Long noticeId, User user) {
        Notification notification = notificationRepository.findById(noticeId).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 알림입니다."));

        notificationRepository.delete(notification);

        return new ApiResponseDto("알림 삭제 성공", HttpStatus.OK.value());
    }

    @Override
    public List<NotificationResponseDto> getNotifications(UserDetailsImpl userDetails) {
        return notificationRepository.findAllByUserIdOrderByCreatedDateDESC(userDetails.getUser().getUserId())
                .stream()
                .map(NotificationResponseDto::new)
                .toList();
    }
}
