package com.brick.controller;

import com.brick.dto.LoginRequestDto;
import com.brick.dto.SignUpRequestDto;
import com.brick.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    //회원가입 !
    @PostMapping("/signup")
    public  ResponseEntity<Long> signup(@RequestBody SignUpRequestDto dto) {
        Long userId = authService.SingUp(dto);
        return ResponseEntity.ok(userId);
    }

    //로그인 !
    @PostMapping("/login")
    public ResponseEntity<Long> login(@RequestBody LoginRequestDto dto) {
        Long userId = authService.login(dto);
        return ResponseEntity.ok(userId);
    }
}
