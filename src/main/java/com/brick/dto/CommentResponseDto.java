package com.brick.dto;

import lombok.Builder;

import java.time.LocalDateTime;

public class CommentResponseDto {
    private Long id;
    private Long userId;
    private String nickname;
    private String content;
    private LocalDateTime createdAt;

    // 서버가 직접 객체 생성해서 반환해야 하기에 필요
    @Builder
    public CommentResponseDto(Long id, Long userId , String nickname , String content , LocalDateTime createdAt) {
        this.id = id;
        this.userId = userId;
        this.nickname = nickname;
        this.content = content;
        this.createdAt = createdAt;
    }
}
