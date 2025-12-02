package com.brick.service;

import com.brick.dto.UserResponseDto;

public interface UserService {
    UserResponseDto getUser(Long userId);
}
