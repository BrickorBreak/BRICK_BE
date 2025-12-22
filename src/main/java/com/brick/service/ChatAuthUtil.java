package com.brick.service;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.security.Principal;

public class ChatAuthUtil {

    private ChatAuthUtil(){

    }

    public static Long getUserIdFormSecurityContext(){
        // SecurityContextHolder에 로그인 정보 저장
        // **REST(HTTP 요청)**에서 “스프링 시큐리티가 저장해둔 로그인 정보”에서 꺼냄
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
            throw new RuntimeException("인증 정보 없습니다.");
        }
        Object principal = auth.getPrincipal(); // 인터셉터에서 principal이 userId
        if(principal instanceof Long l ) return l;
        if(principal instanceof String s ) return Long.parseLong(s);

        throw new RuntimeException("principal 타입을 알 수 없습니다: " + principal);
    }

    public static Long getUserIdFormPrincipal(Principal principal){
        // **WebSocket(STOMP 메시지 처리)**에서 “메시지에 붙어 들어온 Principal”에서 꺼냄
        if(principal == null) throw new RuntimeException("인증 정보가 없습니다.");

        if(principal instanceof Authentication auth){
            Object p  = auth.getPrincipal();
            if( p instanceof  Long l ) return l;
            if( p instanceof String s ) return Long.parseLong(s);
        }
        return Long.parseLong(principal.getName());
    }
}
