package com.sparta.and.aop;

import com.sparta.and.security.UserDetailsImpl;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

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

    @Pointcut("execution(* com.sparta..*Controller.deleteRoom(..))")
    public void getRoom() {}

    @Pointcut("execution(* com.sparta..*Controller.deleteRoom(..))")
    public void deleteRoom() {}

    @Pointcut("execution(* com.sparta..*Controller.getRooms(..))")
    public void getRooms() {}

    /**
     * arg[1]에 @AuthenticationPrincipal이 존재
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("createComment() || updateComment() || deleteComment() || reportComment() || createRoom() || getRoom() || deleteRoom()")
    public void executeLoginCheck_MultiArgs(JoinPoint joinPoint) throws Throwable {
        log.info("If method executing, Need Login");
        Object[] argument = joinPoint.getArgs();
        UserDetailsImpl user = (UserDetailsImpl) argument[1];

        if(user == null) {
            throw new RejectedExecutionException("로그인이 필요합니다.");
        }
    }


    /**
     * arg[0]에 @AuthenticationPrincipal이 존재
     *
     * @param joinPoint
     * @throws Throwable
     */
    @Before("getRooms()")
    public void executeLoginCheck_SingleArgs(JoinPoint joinPoint) throws Throwable {
        log.info("If method executing, Need Login");
        Object[] argument = joinPoint.getArgs();
        UserDetailsImpl user = (UserDetailsImpl) argument[0];

        if(user == null) {
            throw new RejectedExecutionException("로그인이 필요합니다.");
        }
    }

}
