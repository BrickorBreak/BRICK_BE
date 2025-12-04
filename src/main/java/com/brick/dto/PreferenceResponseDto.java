package com.brick.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Builder
// 서버 -> 사용자
public class PreferenceResponseDto {
    private Long foodId;
    private String name;
}
