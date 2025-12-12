package com.brick.service;

import com.brick.entity.Feed;
import com.brick.entity.FeedImage;
import com.brick.repository.FeedImageRepository;
import com.brick.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedImageRepository feedImageRepository;

    private final String DEFAULT_IMAGE_URL = "https://default-image.jpg";
    //이거 기본 이미지로 채워져서 4시에 자동 업로드 되도록 하는 거여서 기본 이미지 필요

    public void saveImage(Long userId, String imageUrl, Long foodId) {

        LocalDateTime now = LocalDateTime.now();

        LocalDate feedDate =
                now.getHour() < 4 ? now.toLocalDate().minusDays(1)
                        : now.toLocalDate();

        Feed feed = feedRepository
                .findByUserIdAndFeedDate(userId, feedDate)
                .orElseGet(() -> feedRepository.save(
                        Feed.builder()
                                .userId(userId)
                                .feedDate(feedDate)
                                .isCompleted(false)
                                .createdAt(now)
                                .build()
                ));


        if (feed.getIsCompleted()) {
            throw new RuntimeException("이미 확정된 피드입니다.");
        }

        int imageCount = feedImageRepository.countByFeedId(feed.getFeedId());

        if (imageCount >= 9) {
            throw new RuntimeException("이미 9장을 모두 채웠습니다.");
        }

        FeedImage image = FeedImage.builder()
                .feedId(feed.getFeedId())
                .userId(userId)
                .imageUrl(imageUrl)
                .sequence(imageCount + 1)
                .takenTime(now)
                .foodCategoryId(foodId)
                .build();

        feedImageRepository.save(image);

    }

    @Scheduled(cron = "0 0 4 * * *")
    public void completeFeedsAt4AM() {

        List<Feed> incompleteFeeds = feedRepository.findByIsCompletedFalse();

        for (Feed feed : incompleteFeeds) {
            int currentCount = feedImageRepository.countByFeedId(feed.getFeedId());

            if (currentCount < 9) {
                int need = 9 - currentCount;

                for (int i = 0; i < need; i++) {
                    FeedImage defaultImg = FeedImage.builder()
                            .feedId(feed.getFeedId())
                            .userId(feed.getUserId())
                            .imageUrl(DEFAULT_IMAGE_URL)
                            .sequence(currentCount + i + 1)
                            .takenTime(feed.getFeedDate().atTime(4, 0))
                            .build();

                    feedImageRepository.save(defaultImg);
                }
            }

            feed.setIsCompleted(true);
            feedRepository.save(feed);
        }
    }

    //마이페이지
    public List<Feed> getUserFeeds(Long userId) {
        return feedRepository.findByUserId(userId);
    }

    //특정 피드 9장 조회
    public List<FeedImage> getFeedImages(Long feedId) {
        List<FeedImage> images =
                feedImageRepository.findByFeedIdOrderBySequenceAsc(feedId);

        return images == null ? List.of() : images;
    }

    //홈 전체 피드
    public List<Feed> getAllFeeds() {
        return feedRepository.findAll();
    }
}