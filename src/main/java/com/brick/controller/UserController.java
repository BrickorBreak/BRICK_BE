package com.brick.controller;

import com.brick.dto.UserRequestDto;
import com.brick.dto.UserResponseDto;
import com.brick.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 내 정보 조회
     * JWT에서 userId 자동 추출
     */
    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUser(
            @AuthenticationPrincipal Long userId
    ) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    /**
     * 내 정보 수정
     * JWT에서 userId 자동 추출
     */
    @PutMapping("/me")
    public ResponseEntity<Void> updateMyUser(
            @AuthenticationPrincipal Long userId,
            @RequestBody UserRequestDto dto
    ) {
        userService.updateUser(userId, dto);
        return ResponseEntity.ok().build();
    }
}
