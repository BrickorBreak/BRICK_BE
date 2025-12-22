package com.brick.service;

import com.brick.dto.CreateRoomResponse;
import com.brick.entity.ChatRoom;
import com.brick.entity.ChatRoomMember;
import com.brick.entity.User;
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
public class ChatRoomServiceImpl implements ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomMemberRepository chatRoomMemberRepository;
    private final UserRepository userRepository;

    @Override
    public CreateRoomResponse createPrivateRoom(Long requesterId, Long targetUserId) {
        if(requesterId.equals(targetUserId)){
            throw new RuntimeException("자기 자신과 채팅방을 만들 수 없습니다.");
        }

        User user = userRepository.findById(requesterId)
                .orElseThrow(() -> new RuntimeException("유저 없슴 ㅎ"));
        User target = userRepository.findById(targetUserId)
                .orElseThrow(()-> new RuntimeException("상대 유저 없음 ㅎ"));

        // 방 만들고
        ChatRoom room = chatRoomRepository.save(new ChatRoom());
        // 방에 누구 있는지 등록
        chatRoomMemberRepository.save(new ChatRoomMember(room,user));
        chatRoomMemberRepository.save(new ChatRoomMember(room,target));

        return new CreateRoomResponse(room.getRoomId());
    }

    // 내 방 조회
    @Override
    @Transactional(readOnly = true)
    public List<Long> getMyRoomIds(Long requesterId) {
        return chatRoomMemberRepository.findRoomIdsByUserId(requesterId);
    }
}
