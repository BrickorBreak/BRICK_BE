package com.brick.controller;

import com.brick.dto.TopFoodStatsResponse;
import com.brick.service.StatsService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users/me/stats")
public class StatsController {

    private final StatsService statsService;

    // 예: GET /api/v1/users/me/stats/top-food?days=30
    @GetMapping("/top-food")
    public TopFoodStatsResponse topFood(
            @AuthenticationPrincipal Long userId,
            @RequestParam(defaultValue = "30") int days
    ) {
        return statsService.getMyTopFoodCategory(userId, days);
    }
}
