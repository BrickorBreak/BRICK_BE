package com.brick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class ChatRoomSummaryResponse {

    private Long roomId;

    // 상대방 정보
    private Long targetUserId;
    private String targetNickname;
    private String targetProfileImage; // 없으면 null

    // 마지막 메시지
    private String lastMessage;
    private LocalDateTime lastMessageTime;
}
