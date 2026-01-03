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
            throw new RuntimeException("메세지 내용이 비었습니다.");
        }

        // 방 멤버 검증
        boolean isMember =
                chatRoomMemberRepository.existsByRoom_RoomIdAndUser_UserId(roomId, senderId);
        if (!isMember) {
            throw new RuntimeException("해당 방의 멤버가 아닙니다.");
        }

        // 채팅방 조회
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(() -> new RuntimeException("채팅방이 없습니다."));

        // 보낸 사람 조회
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("유저가 없습니다."));

        //메시지 저장
        ChatMessage saved =
                chatMessageRepository.save(new ChatMessage(room, sender, content));

        // 완전한 응답 DTO 반환
        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(Long roomId, Long requesterId) {

        // 멤버 검증
        boolean isMember =
                chatRoomMemberRepository.existsByRoom_RoomIdAndUser_UserId(roomId, requesterId);
        if (!isMember) {
            throw new RuntimeException("해당 방의 멤버가 아닙니다.");
        }

        return chatMessageRepository
                .findByRoom_RoomIdOrderByCreatedAtAsc(roomId)
                .stream()
                .map(this::toResponse)
                .toList();
    }

    /**
     * ChatMessage → ChatMessageResponse 변환
     * (메시지 + 보낸 사람 정보까지 포함)
     */
    private ChatMessageResponse toResponse(ChatMessage m) {
        User sender = m.getSender();

        return new ChatMessageResponse(
                m.getMessageId(),
                m.getRoom().getRoomId(),
                sender.getUserId(),
                sender.getNickName(),      // senderName
                sender.getImageUrl(),      // senderImageUrl
                m.getContent(),
                m.getCreatedAt()
        );
    }
}
