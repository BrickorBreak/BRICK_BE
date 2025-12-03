package com.brick.controller;

import com.brick.dto.UserRequestDto;
import com.brick.dto.UserResponseDto;
import com.brick.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users") // 기본 URL 로 사용하겠다는 의미
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

        @GetMapping("/{userId}")
        // userId를 변수로 받아옴
        // <UserResponseDto> 응답 body에 들어갈 데이터 형태
        public ResponseEntity<UserResponseDto>getUser(@PathVariable Long userId) {
            return ResponseEntity.ok(userService.getUser(userId));
            // userId로 유저 정보
        }

        @PutMapping("/{userId}")
        public ResponseEntity<Void> updateUser(
                @PathVariable Long userId,
                @RequestBody UserRequestDto dto
        ) {
            userService.updateUser(userId, dto);
            return ResponseEntity.ok().build();
        }
}
