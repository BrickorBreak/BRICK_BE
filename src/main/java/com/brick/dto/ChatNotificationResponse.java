package com.brick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatNotificationResponse {

    private Long roomId;        // 어디서 온 메시지인지
    private Long senderId;
    private String senderName;
    private String content;     // 메시지 미리보기
    private LocalDateTime createdAt;
}
