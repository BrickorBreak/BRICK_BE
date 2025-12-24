package com.brick.dto;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopFoodStatsResponse {
    private Integer days;               // 조회 기간 (30일)
    private Long foodCategoryId;         // 가장 많이 나온 카테고리 ID
    private String foodCategoryName;     // 카테고리명
    private Long count;                 // 해당 카테고리 사진 수
    private LocalDateTime from;          // 시작 시간
    private LocalDateTime to;            // 끝 시간
}
