package org.zerock.myapp.config;

import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.*;
import org.springframework.security.web.authentication.session.SessionFixationProtectionEvent;
import org.springframework.stereotype.Component;

/**
 * Spring Security와 관련된 이벤트 리스너 클래스입니다.
 *
 * 이 클래스는 Spring Security가 제공하는 다양한 인증 관련 이벤트를 수신하여
 * 해당 이벤트가 발생할 때마다 로그를 남깁니다.
 */
@Log4j2
@NoArgsConstructor

@Component  // 이 클래스를 스프링 빈으로 등록합니다.
public class SecurityEventListener {

    // 1. 인증 성공 관련 이벤트 리스너들

    /**
     * 인증 성공 이벤트를 처리하는 메서드.
     *
     * @param event 인증 성공 이벤트 객체
     */
    @EventListener
    public void handleAuthenticationSuccess(AuthenticationSuccessEvent event){
        log.trace("1. handleAuthenticationSuccess({}) invoked.", event);
    }

    /**
     * 세션 고정 보호 이벤트를 처리하는 메서드.
     *
     * @param event 세션 고정 보호 이벤트 객체
     */
    @EventListener
    public void handleSessionFixationProtection(SessionFixationProtectionEvent event){
        log.trace("2. handleSessionFixationProtection({}) invoked.", event);
    }

    /**
     * 대화형 인증 성공 이벤트를 처리하는 메서드.
     *
     * @param event 대화형 인증 성공 이벤트 객체
     */
    @EventListener
    public void handleInteractiveAuthenticationSuccess(InteractiveAuthenticationSuccessEvent event){
        log.trace("3. handleInteractiveAuthenticationSuccess({}) invoked.", event);
    }

    // 2. 인증 실패 관련 이벤트 리스너들

    /**
     * 잘못된 자격 증명으로 인한 인증 실패 이벤트를 처리하는 메서드.
     */
    @EventListener
    public void handleBadCredential(AuthenticationFailureBadCredentialsEvent event){
        log.trace("1. handleBadCredential({}) invoked.", event);
    }

    /**
     * 비활성화된 계정으로 인한 인증 실패 이벤트를 처리하는 메서드.
     */
    @EventListener
    public void handleDisabled(AuthenticationFailureDisabledEvent event){
        log.trace("2. handleDisabled({}) invoked.", event);
    }

    /**
     * 만료된 계정으로 인한 인증 실패 이벤트를 처리하는 메서드.
     */
    @EventListener
    public void handleExpired(AuthenticationFailureExpiredEvent event){
        log.trace("3. handleExpired({}) invoked.", event);
    }

    /**
     * 잠긴 계정으로 인한 인증 실패 이벤트를 처리하는 메서드.
     */
    @EventListener
    public void handleLocked(AuthenticationFailureLockedEvent event){
        log.trace("4. handleLocked({}) invoked.", event);
    }

    /**
     * 자격 증명이 만료된 경우의 인증 실패 이벤트를 처리하는 메서드.
     */
    @EventListener
    public void handleCredentialsExpired(AuthenticationFailureCredentialsExpiredEvent event){
        log.trace("5. handleCredentialsExpired({}) invoked.", event);
    }

    /**
     * 제공자를 찾지 못한 경우의 인증 실패 이벤트를 처리하는 메서드.
     */
    @EventListener
    public void handleProviderNotFound(AuthenticationFailureProviderNotFoundEvent event){
        log.trace("6. handleProviderNotFound({}) invoked.", event);
    }

    /**
     * 서비스 예외로 인한 인증 실패 이벤트를 처리하는 메서드.
     */
    @EventListener
    public void handleServiceException(AuthenticationFailureServiceExceptionEvent event){
        log.trace("7. handleServiceException({}) invoked.", event);
    }

} // end class
