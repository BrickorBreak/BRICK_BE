package com.brick.controller;

import com.brick.dto.PreferenceRequestDto;
import com.brick.dto.PreferenceResponseDto;
import com.brick.service.UserPreferenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // API 응답 받는 컨트롤러 나타냄
@RequestMapping("/api/v1/users") // 여기서 붙여서 씀
@RequiredArgsConstructor // 자동 생성자 주입
public class UserPreferenceController {
    private final UserPreferenceService userPreferenceService; // 서비스 불러서 실제 로직

    @PostMapping("/{userId}/preferences")
    public ResponseEntity<Void> savePreferences(
            @PathVariable Long userId, // users/5/preferences -> 5를 변수로 받음
            @RequestBody PreferenceRequestDto request // 자바 객체로 변환해서 받는 것
    ) {
        userPreferenceService.saveUserPreferences(userId, request.getPreferences());
        return ResponseEntity.ok().build(); // body없이 ok 만 !
    }

    @GetMapping("/{userId}/preferences")
    public ResponseEntity<List<PreferenceResponseDto>> getPreferences(
        @PathVariable Long userId
    ){
        List<PreferenceResponseDto> result = userPreferenceService.getUserPreferences(userId);
        return ResponseEntity.ok(result);
    }

}
