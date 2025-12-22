package com.brick.service;

import com.brick.dto.CreateRoomResponse;
import java.util.List;

public interface ChatRoomService {
    CreateRoomResponse createPrivateRoom(Long requesterId, Long targetUserId);
    List<Long> getMyRoomIds(Long requesterId);
}
