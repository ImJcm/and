package com.sparta.and.repository;

import com.sparta.and.entity.Notification;

import java.util.List;

public interface NotificationRepositoryQuery {

    /**
     * Receiver에 해당하는 사용자에게 전달된 모든 Notification 조회
     *
     * @param userId
     * @return
     */
    List<Notification> findAllByUserIdOrderByCreatedDateDESC(Long userId);


}
