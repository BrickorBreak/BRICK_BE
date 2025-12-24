package com.brick.service;

import com.brick.dto.TopFoodStatsResponse;
import com.brick.entity.FoodCategory;
import com.brick.repository.FoodCategoryRepository;
import com.brick.repository.StatsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class StatsServiceImpl implements StatsService {

    private final StatsRepository statsRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    @Override
    public TopFoodStatsResponse getMyTopFoodCategory(Long userId, int days) {
        LocalDateTime to = LocalDateTime.now();
        LocalDateTime from = to.minusDays(days);

        return statsRepository.findTopFoodCategory(userId, from)
                .map(row -> {
                    FoodCategory fc = foodCategoryRepository.findById(row.foodCategoryId())
                            .orElse(null);

                    return TopFoodStatsResponse.builder()
                            .days(days)
                            .foodCategoryId(row.foodCategoryId())
                            .foodCategoryName(fc != null ? fc.getName() : "Unknown")
                            .count(row.count())
                            .from(from)
                            .to(to)
                            .build();
                })
                // 최근 N일 동안 음식사진이 0장이면 null 대신 없음 형태로 내려줌
                .orElse(
                        TopFoodStatsResponse.builder()
                                .days(days)
                                .foodCategoryId(null)
                                .foodCategoryName("데이터 없음")
                                .count(0L)
                                .from(from)
                                .to(to)
                                .build()
                );
    }
}
