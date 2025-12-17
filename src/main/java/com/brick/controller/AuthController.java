package com.brick.controller;

import com.brick.dto.LoginRequestDto;
import com.brick.dto.SignUpRequestDto;
import com.brick.security.JwtTokenProvider;
import com.brick.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<Long> signup(@RequestBody SignUpRequestDto dto) {
        Long userId = authService.SingUp(dto);
        return ResponseEntity.ok(userId);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto dto) {

        Long userId = authService.login(dto);  // userId
        String token = jwtTokenProvider.createToken(userId); // JWT 생성

        return ResponseEntity.ok(
                Map.of(
                        "userId", userId,
                        "accessToken", token
                )
        );
    }
}
