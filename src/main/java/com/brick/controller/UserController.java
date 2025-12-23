package com.brick.controller;

import com.brick.dto.UserRequestDto;
import com.brick.dto.UserResponseDto;
import com.brick.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.security.core.annotation.AuthenticationPrincipal;


@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<UserResponseDto> getMyUser(
            @AuthenticationPrincipal Long userId
    ) {
        return ResponseEntity.ok(userService.getUser(userId));
    }

    @PutMapping(
            value = "/me",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    public ResponseEntity<Void> updateMyUser(
            @AuthenticationPrincipal Long userId,
            @RequestPart("data") @Valid UserRequestDto dto,
            @RequestPart(value = "image", required = false) MultipartFile image
    ) {
        userService.updateUser(userId, dto, image);
        return ResponseEntity.ok().build();
    }
}
