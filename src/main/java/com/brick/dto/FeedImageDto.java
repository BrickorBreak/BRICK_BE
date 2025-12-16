package com.brick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class FeedImageDto {
    private String imageUrl;
    private Integer sequence;
    private LocalDateTime takenTime;
}
