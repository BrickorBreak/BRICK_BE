package com.brick.controller;

import com.brick.service.PhotoService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/v1/photos")
@RequiredArgsConstructor
public class PhotoController {

    private final PhotoService photoService;

    @PostMapping
    public void uploadPhoto(
            @AuthenticationPrincipal Long userId,
            @RequestParam("image") MultipartFile image,
            @RequestParam("categoryId") Long categoryId,
            @RequestParam("confidence") Double confidence
    ) {
        photoService.savePhoto(userId, image, categoryId, confidence);
    }
}
