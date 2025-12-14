package com.brick.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedImageDto {
    private String imageUrl;
    private Integer sequence;
}
