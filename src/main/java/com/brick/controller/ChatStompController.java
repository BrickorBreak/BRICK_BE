package com.brick.controller;

import com.brick.dto.ChatMessageResponse;
import com.brick.service.ChatMessageService;
import com.brick.dto.ChatMessageRequest;
import lombok.RequiredArgsConstructor;
import com.brick.service.ChatAuthUtil;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatStompController {

    private final ChatMessageService chatMessageService;
    private final SimpMessagingTemplate messagingTemplate; // “서버가 구독자들에게 메시지 보내는 스피커”

    // 클라이언트가 /app/chat/rooms/{roomId}/send 로 보내면 여기로 들어옴
    @MessageMapping("/chat/rooms/{roomId}/send")
    public void sendMessage(@DestinationVariable Long roomId,
                            @Payload ChatMessageRequest request,
                            Principal principal) {
        try {
            System.out.println("🔥 HIT roomId=" + roomId);
            Long senderId = ChatAuthUtil.getUserIdFormPrincipal(principal);

            ChatMessageResponse response =
                    chatMessageService.send(roomId, senderId, request.getContent());

            System.out.println("🔥 BROADCAST to /topic/rooms/" + roomId);
            messagingTemplate.convertAndSend("/topic/rooms/" + roomId, response);

        } catch (Exception e) {
            System.out.println("💥 sendMessage ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
