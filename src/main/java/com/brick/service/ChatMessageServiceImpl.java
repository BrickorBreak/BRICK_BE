package com.brick.service;

import com.brick.dto.ChatMessageResponse;
import com.brick.entity.ChatMessage;
import com.brick.entity.ChatRoom;
import com.brick.entity.User;
import com.brick.repository.ChatMessageRepository;
import com.brick.repository.ChatRoomMemberRepository;
import com.brick.repository.ChatRoomRepository;
import com.brick.repository.UserRepository;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatMessageServiceImpl implements ChatMessageService{
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final UserRepository userRepository;


    @Override
    public ChatMessageResponse send(Long roomId, Long senderId, String content) {
        // 메세지 내용 있는지 검사
        if(content == null || content.trim().isEmpty()){
            throw new RuntimeException("메세지 내용이 비었어요 ^^ ");
        }
        // 방마다 멤버 체크
        boolean isMember = chatRoomMemberRepository.existsByRoom_RoomIdAndUser_UserId(roomId,senderId);
        if(!isMember){
            throw new RuntimeException("해당 방의 멤버가 아닙니다");
        }
        // 채팅방
        ChatRoom room = chatRoomRepository.findById(roomId)
                .orElseThrow(()-> new RuntimeException("채팅방이 없습니다"));
        // 유저 존재
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("유저가 없숨"));

        ChatMessage saved = chatMessageRepository.save(new ChatMessage(room,sender,content));

        return toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ChatMessageResponse> getMessages(Long roomId, Long requestId) {
        // 멤버인지 확인
        boolean isMember = chatRoomMemberRepository.existsByRoom_RoomIdAndUser_UserId(roomId,requestId);
        if(!isMember){
            throw new RuntimeException("해당 방의 멤버가 아닙니다");
        }

        return chatMessageRepository.findByRoom_RoomIdOrderByCreatedAtAsc(roomId)
                .stream() // 리스트에 하나씩 꺼내서 처리할 수 있는 줄로 바꿔줌
                .map(this::toResponse) // 메세지 하나하나를 toResponse로 변환함
                .toList(); // 리스토로 모아서 반환
    }
    private ChatMessageResponse toResponse(ChatMessage m){
        return new ChatMessageResponse(
                m.getMessageId(),
                m.getRoom().getRoomId(),
                m.getSender().getUserId(),
                m.getContent(),
                m.getCreatedAt()
        );
    }
}
