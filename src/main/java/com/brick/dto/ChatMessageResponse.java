package com.brick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

// 방에 메세지 도착했으면 프론트에 띄울 정보
@Getter
@AllArgsConstructor
public class ChatMessageResponse {
    private Long messageId;
    private Long roomId;
    private Long senderId;
    private String content;
    private LocalDateTime createdAt;
}
