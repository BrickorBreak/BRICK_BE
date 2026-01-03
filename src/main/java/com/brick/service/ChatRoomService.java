package com.brick.service;

import com.brick.dto.CreateRoomResponse;
import java.util.List;
import com.brick.dto.ChatRoomSummaryResponse;

public interface ChatRoomService {
    CreateRoomResponse createPrivateRoom(Long requesterId, Long targetUserId);
    List<Long> getMyRoomIds(Long requesterId);

    List<ChatRoomSummaryResponse> getMyChatRooms(Long requesterId);

}
