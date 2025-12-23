package com.brick.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateRoomRequest {
    private Long targetUserId; // 보낼 사람
}
