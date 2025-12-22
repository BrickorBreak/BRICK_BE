package com.brick.controller;

import com.brick.dto.CreateRoomRequest;
import com.brick.dto.CreateRoomResponse;
import com.brick.service.ChatRoomService;
import com.brick.service.ChatAuthUtil; // 만약 너 util 패키지가 다르면 수정
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/chat")
public class ChatRoomController {

    private final ChatRoomService chatRoomService;

    // 1:1 방 생성
    @PostMapping("/rooms")
    // POST api/v1/chat/rooms 요청 받으면 @RequestBody 프론트가 보내는 JSON 데이터를 자바 객체
    public CreateRoomResponse createRoom(@RequestBody CreateRoomRequest request) {
        Long requesterId = ChatAuthUtil.getUserIdFormSecurityContext();
        return chatRoomService.createPrivateRoom(requesterId, request.getTargetUserId());
    }

    // 내가 속한 방 목록
    @GetMapping("/rooms")
    public List<Long> getMyRooms() {

        Long requesterId = ChatAuthUtil.getUserIdFormSecurityContext();
        return chatRoomService.getMyRoomIds(requesterId);
    }
}
