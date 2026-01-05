package com.brick.service;

import com.brick.dto.ChatMessageResponse;
import com.brick.entity.ChatMessage;
import com.brick.entity.ChatRoom;
import com.brick.entity.User;
import com.brick.repository.ChatMessageRepository;
import com.brick.repository.ChatRoomMemberRepository;
import com.brick.repository.ChatRoomRepository;
import com.brick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService {

    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;

    @Override
    public ChatMessageResponse send(Long roomId, Long senderId, String content) {

        // 메시지 내용 검증
        if (content == null || content.trim().isEmpty()) {
            throw new IllegalArgumentException("메시지 내용이 비어있습니다.");
        }

        // 방 멤버 검증
        if (!chatRoomMemberRepository
                .existsByRoom_RoomIdAndUser_UserId(roomId, senderId)) {
            throw new IllegalStateException("해당 채팅방의 멤버가 아닙니다.");
        }

        // 채팅방 조회
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalStateException("채팅방이 존재하지 않습니다."));

        // 보낸 사람 조회
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalStateException("보낸 유저가 존재하지 않습니다."));

        // 메시지 저장
        ChatMessage message = new ChatMessage(room, sender, content);
        ChatMessage saved = chatMessageRepository.saveAndFlush(message);
        // ↑ flush로 즉시 DB 반영 보장

        // 응답 DTO 반환
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(Long roomId, Long requesterId) {

        // 멤버 검증
        if (!chatRoomMemberRepository
                .existsByRoom_RoomIdAndUser_UserId(roomId, requesterId)) {
            throw new IllegalStateException("해당 채팅방의 멤버가 아닙니다.");
        }

        // 메시지 조회
        return chatMessageRepository
                .findByRoom_RoomIdOrderByCreatedAtAsc(roomId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * Entity → Response DTO 변환
     */
    private ChatMessageResponse toResponse(ChatMessage m) {
        User sender = m.getSender();

        return new ChatMessageResponse(
                m.getMessageId(),
                m.getRoom().getRoomId(),
                sender.getUserId(),
                sender.getNickName(),
                sender.getImageUrl(),
                m.getContent(),
                m.getCreatedAt()
        );
    }
}
