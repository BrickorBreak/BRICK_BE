package com.brick.security;

import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessagingException;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);
        StompCommand cmd = accessor.getCommand();
        if (cmd == null) return message;

        // CONNECT에서 토큰 세팅
        if (StompCommand.CONNECT.equals(cmd)) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            if (authHeader == null) authHeader = accessor.getFirstNativeHeader("authorization"); // 케이스 보완

            if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                throw new MessagingException("WebSocket Authorization header missing");
            }

            String token = authHeader.substring(7);

            if (!jwtTokenProvider.validateToken(token)) {
                throw new MessagingException("WebSocket token invalid");
            }

            Long userId = jwtTokenProvider.getUserId(token);

            var authentication =
                    new UsernamePasswordAuthenticationToken(userId, null, List.of());

            accessor.setUser(authentication);
        }

        // SEND 시점에 Principal 없으면 막기
        if (StompCommand.SEND.equals(cmd)) {
            if (accessor.getUser() == null) {
                throw new MessagingException("WebSocket user(principal) is null");
            }
        }
        return message;
    }

}
