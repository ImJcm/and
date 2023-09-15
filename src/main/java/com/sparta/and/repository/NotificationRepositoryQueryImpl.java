package com.sparta.and.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.and.entity.Notification;
import com.sparta.and.entity.QNotification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryQueryImpl implements NotificationRepositoryQuery {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public List<Notification> findAllByUserIdOrderByCreatedDateDESC(Long userId) {
        QNotification notification = QNotification.notification;

        return jpaQueryFactory.selectFrom(notification)
                .where(notification.receiver.userId.eq(userId))
                .orderBy(notification.createdDate.desc())
                .fetch();
    }
}
