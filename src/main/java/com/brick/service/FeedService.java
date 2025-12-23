package com.brick.service;

import com.brick.dto.FeedImageDto;
import com.brick.dto.HomeFeedResponse;
import com.brick.entity.Feed;
import com.brick.entity.FeedImage;
import com.brick.entity.User;
import com.brick.entity.UserFoodPreference;
import com.brick.repository.FeedImageRepository;
import com.brick.repository.FeedRepository;
import com.brick.repository.UserFoodPreferenceRepository;
import com.brick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedService {

    private final FeedRepository feedRepository;
    private final FeedImageRepository feedImageRepository;
    private final UserRepository userRepository;
    private final UserFoodPreferenceRepository userFoodPreferenceRepository;

    private final String DEFAULT_IMAGE_URL = "https://default-image.jpg";


    // 이미지 저장

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

    // 매일 새벽 4시 피드 확정

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


    // 마이페이지 피드

    public List<Feed> getUserFeeds(Long userId) {
        return feedRepository.findByUserId(userId);
    }


    // 특정 피드 이미지 9장

    public List<FeedImage> getFeedImages(Long feedId) {
        List<FeedImage> images =
                feedImageRepository.findByFeedIdOrderBySequenceAsc(feedId);
        return images == null ? List.of() : images;
    }


    //전체 피드 (테스트용)

    public List<Feed> getAllFeeds() {
        return feedRepository.findAll();
    }


    // 메인 홈 피드

    public List<HomeFeedResponse> getHomeFeeds() {
        List<Feed> feeds =
                feedRepository.findByIsCompletedTrueOrderByFeedDateDesc();

        return buildHomeFeedResponses(feeds);
    }


    // 음식 카테고리별 홈 피드

    public List<HomeFeedResponse> getFeedsByFoodCategory(Long foodCategoryId) {

        List<UserFoodPreference> prefs =
                userFoodPreferenceRepository
                        .findByFoodCategory_FoodCategoryId(foodCategoryId);

        if (prefs.isEmpty()) return List.of();

        List<Long> userIds = prefs.stream()
                .map(p -> p.getUser().getUserId())
                .distinct()
                .toList();

        List<Feed> feeds =
                feedRepository
                        .findByUserIdInAndIsCompletedTrueOrderByFeedDateDesc(userIds);

        return buildHomeFeedResponses(feeds);
    }


    // 공통 HomeFeedResponse 생성 로직

    private List<HomeFeedResponse> buildHomeFeedResponses(List<Feed> feeds) {

        List<HomeFeedResponse> result = new ArrayList<>();

        for (Feed feed : feeds) {
            User user = userRepository.findById(feed.getUserId())
                    .orElseThrow();

            List<FeedImage> feedImages =
                    feedImageRepository
                            .findByFeedIdOrderBySequenceAsc(feed.getFeedId());

            List<FeedImageDto> imageDtos = new ArrayList<>();
            for (FeedImage img : feedImages) {
                imageDtos.add(
                        new FeedImageDto(
                                img.getImageUrl(),
                                img.getSequence(),
                                img.getTakenTime()
                        )
                );
            }

            result.add(
                    new HomeFeedResponse(
                            feed.getFeedId(),
                            feed.getFeedDate(),

                            user.getUserId(),
                            user.getNickName(),
                            user.getImageUrl(),
                            user.getAge(),
                            user.getCity(),
                            user.getDistrict(),
                            user.getIntro(),

                            imageDtos
                    )
            );
        }

        return result;
    }
}
