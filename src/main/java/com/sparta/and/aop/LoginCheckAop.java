package com.sparta.and.aop;

import com.sparta.and.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.concurrent.RejectedExecutionException;

@Component
@Aspect
@Slf4j(topic = "loginCheckAOP")
public class LoginCheckAop {
    /**
     *
     * com.sparta.and.controller.CommentController.insertComment(..) - and 표현 인식이 불가능
     */
    @Pointcut("execution(* com.sparta..*Controller.createComment(..))")
    public void createComment() {}

    @Pointcut("execution(* com.sparta..*Controller.updateComment(..))")
    public void updateComment() {}

    @Pointcut("execution(* com.sparta..*Controller.deleteComment(..))")
    public void deleteComment() {}

    @Pointcut("execution(* com.sparta..*Controller.reportComment(..))")
    public void reportComment() {}

    @Pointcut("execution(* com.sparta..*Controller.createRoom(..))")
    public void createRoom() {}

    @Pointcut("execution(* com.sparta..*Controller.getRoom(..))")
    public void getRoom() {}

    @Pointcut("execution(* com.sparta..*Controller.deleteRoom(..))")
    public void deleteRoom() {}

    @Pointcut("execution(* com.sparta..*Controller.getRooms(..))")
    public void getRooms() {}

    @Pointcut("execution(* com.sparta..*Controller.getUser(..))")
    public void getUser() {}

    @Pointcut("execution(* com.sparta..*Controller.enter(..))")
    public void enter() {}

    @Pointcut("execution(* com.sparta..*Controller.message(..))")
    public void message() {}

    @Pointcut("execution(* com.sparta..*Controller.getNotification(..))")
    public void getNotifications() {}

    @Pointcut("execution(* com.sparta..*Controller.readNotification(..))")
    public void readNotification() {}

    @Pointcut("execution(* com.sparta..*Controller.deleteNotification(..))")
    public void deleteNotification() {}

    /**
     * arg[1]에 @AuthenticationPrincipal이 존재
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("createComment() || updateComment() || deleteComment() || reportComment() || createRoom() || getRoom() || deleteRoom() || enter() || message() || readNotification() || deleteNotification()")
    public void executeLoginCheck_MultiArgs(JoinPoint joinPoint) throws Throwable {
        log.info("If method executing, Need Login");
        Object[] argument = joinPoint.getArgs();

        UserDetailsImpl user = (UserDetailsImpl) argument[1];

        if(user == null) {
            throw new RejectedExecutionException("로그인이 필요합니다");
        }
    }


    /**
     * arg[0]에 @AuthenticationPrincipal이 존재
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("getRooms() || getUser() || getNotifications()")
    public void executeLoginCheck_SingleArgs(JoinPoint joinPoint) throws Throwable {
        log.info("If method executing, Need Login");
        Object[] argument = joinPoint.getArgs();
        UserDetailsImpl user = (UserDetailsImpl) argument[0];

        if(user == null) {
            throw new RejectedExecutionException("로그인이 필요합니다");
        }
    }

}
