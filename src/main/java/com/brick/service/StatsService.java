package com.brick.service;

import com.brick.dto.TopFoodStatsResponse;

public interface StatsService {
    TopFoodStatsResponse getMyTopFoodCategory(Long userId, int days);
}
