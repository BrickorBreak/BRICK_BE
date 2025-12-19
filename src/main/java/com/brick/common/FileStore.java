package com.brick.common;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Component
public class FileStore {

    private final String uploadDir = "uploads/";

    public String save(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(uploadDir));

            String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());

            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("파일 저장 실패");
        }
    }
}
