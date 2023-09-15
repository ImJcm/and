package com.sparta.and.dto.response;

import com.sparta.and.entity.Notification;
import com.sparta.and.entity.TimeStamped;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotificationResponseDto {
    private Long notificationId;
    private String content;
    private String url;
    private Boolean isRead;
    private String receiver;
    private String notificationType;
    private String notificationDate;

    public NotificationResponseDto(Notification notification) {
        this.notificationId = notification.getId();
        this.content = notification.getContent();
        this.url = notification.getUrl();
        this.isRead = notification.getIsRead();
        this.receiver = notification.getReceiver().getUserName();
        this.notificationType = String.valueOf(notification.getNotificationType());
        this.notificationDate = notification.getCreatedDateFormatted(TimeStamped.FORMATTER_DATE_HOUR_MINUTE);
    }
}
