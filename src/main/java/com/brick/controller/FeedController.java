package com.brick.controller;

import com.brick.dto.HomeFeedResponse;
import com.brick.entity.Feed;
import com.brick.entity.FeedImage;
import com.brick.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

   
    // 이미지 저장

    @PostMapping("/image")
    public void saveImage(
            @AuthenticationPrincipal Long userId,
            @RequestParam String imageUrl,
            @RequestParam(required = false) Long foodId
    ) {
        feedService.saveImage(userId, imageUrl, foodId);
    }


    // 내 피드

    @GetMapping("/my")
    public List<Feed> myFeeds(
            @AuthenticationPrincipal Long userId
    ) {
        return feedService.getUserFeeds(userId);
    }


    // 🖼특정 피드 이미지

    @GetMapping("/{feedId}/images")
    public List<FeedImage> feedImages(@PathVariable Long feedId) {
        return feedService.getFeedImages(feedId);
    }


    // 전체 피드 (테스트용)

    @GetMapping
    public List<Feed> allFeeds() {
        return feedService.getAllFeeds();
    }


    // 메인 홈 피드

    @GetMapping("/home")
    public List<HomeFeedResponse> homeFeeds() {
        return feedService.getHomeFeeds();
    }


    // 🍽음식 카테고리별 홈 피드

    @GetMapping("/category/{foodCategoryId}")
    public List<HomeFeedResponse> feedsByCategory(
            @PathVariable Long foodCategoryId
    ) {
        return feedService.getFeedsByFoodCategory(foodCategoryId);
    }
}
