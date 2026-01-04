package com.brick.controller;

import com.brick.dto.ChatMessageResponse;
import com.brick.service.ChatAuthUtil;
import com.brick.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatMessageController {

    private final ChatMessageService chatMessageService;

    // 채팅방 메시지 히스토리 조회
    @GetMapping("/rooms/{roomId}/messages")
    public List<ChatMessageResponse> getMessages(@PathVariable Long roomId) {
        Long requesterId = ChatAuthUtil.getUserIdFormSecurityContext();
        return chatMessageService.getMessages(roomId, requesterId);
    }
}
