package com.brick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateRoomResponse {
    // 방 만들고 나서 방 ID를 알려줌
    private Long roomId;
}
