package com.brick.service;

import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
    void savePhoto(
            Long userId,
            MultipartFile image,
            Long categoryId,
            Double confidence
    );
}
