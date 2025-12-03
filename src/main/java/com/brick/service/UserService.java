package com.brick.service;

import com.brick.dto.UserRequestDto;
import com.brick.dto.UserResponseDto;

public interface UserService {
    // 등록
    void updateUser(Long userId , UserRequestDto dto);

    // 조회
    UserResponseDto getUser(Long userId);
}
