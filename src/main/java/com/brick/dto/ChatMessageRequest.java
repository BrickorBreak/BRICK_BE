package com.brick.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 채팅 보낼 때 프론트가 서버로 보내는 요청서
public class ChatMessageRequest {
//    private Long userId; // jwt 쓰니까 굳이 인거 같긴 함 ..
    private String content;

}
