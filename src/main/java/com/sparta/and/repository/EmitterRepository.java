package com.sparta.and.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    /**
     * SSE Emitter 생성
     *
     * @param emitterId         userId + System.millisecond 값으로 id 지정
     * @param sseEmitter        SSE Emitter 생성
     * @return
     */
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    /**
     * 데이터 누락 시, 누락된 알람을 전송하기 위해 Cache에 저장
     *
     * @param eventCacheId      userId + System.millisecond 값으로 id 지정
     * @param event             Notification
     */
    void saveEventCache(String eventCacheId, Object event);

    /**
     * sse 연결이 되지 않은 유저에게 보낸 알림을 보내주기 위한 목적
     * UserId에 해당하는 사용자에게 전달된 알림 Emitter를 받아오기
     *
     * @param userId            사용자 Id
     * @return
     */
    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);

    /**
     * 누락된 알림을 전달하기 위해 Cache에 저장된 알림으로 해결하기 위한 목적
     * userId에 해당하는 사용자아게 전달된 알림 Emitter를 받아오기
     *
     * @param userId
     * @return
     */
    Map<String, Object> findAllEventCacheStartWithByUserId(String userId);

    /**
     * timeout이 지난 Emitter를 삭제
     *
     * @param sseId     sse emitter Id
     */
    void deleteById(String sseId);

    /**
     * userId에 해당하는 Emitter를 삭제
     *
     * @param userId
     */
    void deleteAllEmitterStartWithId(String userId);

    /**
     * userId에 해당하는 Cache Emitter를 삭제
     *
     * @param userId
     */
    void deleteAllEventCacheStartWithId(String userId);
}
