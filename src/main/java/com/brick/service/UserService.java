package com.brick.service;

import com.brick.dto.UserRequestDto;
import com.brick.dto.UserResponseDto;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    // 등록
    void updateUser(Long userId , UserRequestDto dto , MultipartFile image);

    // 조회
    UserResponseDto getUser(Long userId);
}
