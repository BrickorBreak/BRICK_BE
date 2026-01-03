package com.brick.controller;

import com.brick.dto.ChatMessageRequest;
import com.brick.dto.ChatMessageResponse;
import com.brick.dto.ChatNotificationResponse;
import com.brick.service.ChatAuthUtil;
import com.brick.service.ChatMessageService;
import com.brick.repository.ChatRoomMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class ChatStompController {

    private final ChatMessageService chatMessageService;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat/rooms/{roomId}/send")
    public void sendMessage(
            @DestinationVariable Long roomId,
            @Payload ChatMessageRequest request,
            Principal principal
    ) {

        Long senderId = ChatAuthUtil.getUserIdFormPrincipal(principal);

        // 1️⃣ 메시지 저장
        ChatMessageResponse response =
                chatMessageService.send(roomId, senderId, request.getContent());

        // 2️⃣ 채팅방 구독자에게 메시지 전송
        messagingTemplate.convertAndSend(
                "/topic/rooms/" + roomId,
                response
        );

        // 3️⃣ 알림 전송 (보낸 사람 제외)
        chatRoomMemberRepository
                .findByRoom_RoomId(roomId)
                .forEach(member -> {
                    Long targetUserId = member.getUser().getUserId();
                    if (targetUserId.equals(senderId)) return;

                    ChatNotificationResponse notification =
                            new ChatNotificationResponse(
                                    roomId,
                                    senderId,
                                    response.getSenderId().toString(),
                                    response.getContent(),
                                    response.getCreatedAt()
                            );

                    messagingTemplate.convertAndSend(
                            "/topic/notifications/" + targetUserId,
                            notification
                    );
                });
    }
}
