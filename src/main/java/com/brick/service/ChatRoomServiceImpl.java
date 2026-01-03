package com.brick.service;

import com.brick.dto.ChatRoomSummaryResponse;
import com.brick.dto.CreateRoomResponse;
import com.brick.entity.ChatMessage;
import com.brick.entity.ChatRoom;
import com.brick.entity.ChatRoomMember;
import com.brick.entity.User;
import com.brick.repository.ChatMessageRepository;
import com.brick.repository.ChatRoomMemberRepository;
import com.brick.repository.ChatRoomRepository;
import com.brick.repository.UserRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatMessageRepository chatMessageRepository;
    private final UserRepository userRepository;

    @Override
    public CreateRoomResponse createPrivateRoom(Long requesterId, Long targetUserId) {
        if (requesterId.equals(targetUserId)) {
            throw new RuntimeException("자기 자신과 채팅방을 만들 수 없습니다.");
        }

        User user = userRepository.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("유저 없음"));
        User target = userRepository.findById(targetUserId)
                .orElseThrow(() -> new RuntimeException("상대 유저 없음"));

        ChatRoom room = chatRoomRepository.save(new ChatRoom());

        chatRoomMemberRepository.save(new ChatRoomMember(room, user));
        chatRoomMemberRepository.save(new ChatRoomMember(room, target));

        return new CreateRoomResponse(room.getRoomId());
    }

    @Override
    @Transactional(readOnly = true)
    public List<Long> getMyRoomIds(Long requesterId) {
        return chatRoomMemberRepository.findRoomIdsByUserId(requesterId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatRoomSummaryResponse> getMyChatRooms(Long requesterId) {

        List<Long> roomIds =
                chatRoomMemberRepository.findRoomIdsByUserId(requesterId);

        return roomIds.stream().map(roomId -> {

            ChatRoom room = chatRoomRepository.findById(roomId)
                    .orElseThrow();

            ChatRoomMember targetMember =
                    chatRoomMemberRepository.findByRoom_RoomId(roomId).stream()
                            .filter(m -> !m.getUser().getUserId().equals(requesterId))
                            .findFirst()
                            .orElseThrow();

            User target = targetMember.getUser();

            var lastMessageOpt =
                    chatMessageRepository.findTopByRoom_RoomIdOrderByCreatedAtDesc(roomId);

            String lastMessage =
                    lastMessageOpt.map(ChatMessage::getContent).orElse("");

            LocalDateTime lastTime =
                    lastMessageOpt.map(ChatMessage::getCreatedAt).orElse(null);

            return new ChatRoomSummaryResponse(
                    roomId,
                    target.getUserId(),
                    target.getNickName(),
                    target.getImageUrl(),
                    lastMessage,
                    lastTime
            );
        }).toList();
    }
}
