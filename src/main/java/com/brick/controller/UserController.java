package com.brick.controller;

import com.brick.dto.UserRequestDto;
import com.brick.dto.UserResponseDto;
import com.brick.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController // API 요청을 처리하는 컨트롤러
@RequestMapping("/api/v1/users") // 기본 URL 로 사용하겠다는 의미
@RequiredArgsConstructor // final 필드(userService)를 자동으로 생성자 주입

public class UserController {
    private final UserService userService;

        @GetMapping("/{userId}")
        // userId를 변수로 받아옴
        // <UserResponseDto> 응답 body에 들어갈 데이터 형태
        public ResponseEntity<UserResponseDto>getUser(@PathVariable Long userId) {
            //  서비스에 “userId로 유저 정보 조회해줘!”
            return ResponseEntity.ok(userService.getUser(userId));
            // userId로 유저 정보
        }

        @PutMapping("/{userId}")
        public ResponseEntity<Void> updateUser(
                @PathVariable Long userId,
                @RequestBody UserRequestDto dto // 프론트에서 보낸 데이터(request dto)를 dto로 변환해서 읽음
        ) {
            userService.updateUser(userId, dto);
            return ResponseEntity.ok().build();
        }
}
