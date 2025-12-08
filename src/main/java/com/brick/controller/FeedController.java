package com.brick.controller;

import com.brick.entity.Feed;
import com.brick.entity.FeedImage;
import com.brick.service.FeedService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/feeds")
@RequiredArgsConstructor
public class FeedController {

    private final FeedService feedService;

    //카메라에서 사진 1장씩 저장
    @PostMapping("/image")
    public void saveImage(
            @RequestParam Long userId,
            @RequestParam String imageUrl,
            @RequestParam(required = false) Long foodId
    ) {
        feedService.saveImage(userId, imageUrl, foodId);
    }

    //내 마이페이지
    @GetMapping("/my/{userId}")
    public List<Feed> myFeeds(@PathVariable Long userId) {
        return feedService.getUserFeeds(userId);
    }

    //특정 유저 피드의 9개 사진'
    @GetMapping("/my/{userId}")
    public List<FeedImage> feedImages(@PathVariable Long feedId) {
        return feedService.getFeedImages(feedId);
    }

    @GetMapping
    public List<Feed> allFeeds() {
        return feedService.getAllFeeds();
    }

}
