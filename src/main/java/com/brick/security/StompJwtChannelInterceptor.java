package com.brick.security;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;

// STOMP 연결 시점에 헤더의 JWT 검사 -> 웹 소켓 세션의 접속자 심어주는 것
@Component
@RequiredArgsConstructor
public class StompJwtChannelInterceptor implements ChannelInterceptor {
    // ChannerInterceptor 넣어서 채널 들어오기 직전 , 직후에 가로 채는 훅을 제공

    private final JwtTokenProvider jwtTokenProvider;

    //websocket에 들어오는 모든 메세지는 결국 Message<?> , preSend는 메세지 처리 하기 전에 낚아서 수정 권한
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {

        StompHeaderAccessor accessor =
                MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);

        if (accessor == null) return message;

        StompCommand command = accessor.getCommand();
        if (command == null) return message;

        // CONNECT 시점에만 인증 처리
        if (StompCommand.CONNECT.equals(command)) {

            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader == null) {
                authHeader = accessor.getFirstNativeHeader("authorization");
            }

            if (authHeader == null) {
                throw new MessagingException("WebSocket Authorization header missing");
            }

            authHeader = authHeader.trim();

            // "Bearer<token>" 형태 방어
            if (authHeader.startsWith("Bearer") && !authHeader.startsWith("Bearer ")) {
                authHeader = authHeader.replaceFirst("Bearer", "Bearer ");
            }

            if (!authHeader.startsWith("Bearer ")) {
                throw new MessagingException("Invalid Authorization header format");
            }

            String token = authHeader.substring(7).trim();

            if (!jwtTokenProvider.validateToken(token)) {
                throw new MessagingException("WebSocket token invalid");
            }

            Long userId = jwtTokenProvider.getUserId(token);

            Authentication authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, List.of());

            // accessor에 user 설정
            accessor.setUser(authentication);

            // SecurityContext에도 설정
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        // SEND/SUBSCRIBE에서는 막지 말고 로그만
        if (StompCommand.SEND.equals(command) || StompCommand.SUBSCRIBE.equals(command)) {
            if (accessor.getUser() == null) {
                System.err.println("STOMP principal is null on " + command);
            }
        }

        return message;
    }
}