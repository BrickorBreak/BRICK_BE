

package com.brick.service;

import com.brick.common.FileStore;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final FeedImageRepository feedImageRepository;
    private final FileStore fileStore;
    private final UserRepository userRepository;
    private final UserFoodPreferenceRepository userFoodPreferenceRepository;

    @Override
    public void saveImage(
            Long userId,
            MultipartFile image,
            Long categoryId,
            Double confidence
    ) {
        Feed feed = feedRepository
                .findByUserIdAndFeedDate(userId, LocalDate.now())
                .orElseGet(() ->
                        feedRepository.save(
                                Feed.builder()
                                        .userId(userId)
                                        .feedDate(LocalDate.now())
                                        .completed(false)
                                        .createdAt(LocalDateTime.now())
                                        .build()
                        )
                );

        int sequence = feedImageRepository.countByFeedId(feed.getFeedId()) + 1;

        if (sequence > 9) {
            throw new RuntimeException("이미 9장을 모두 저장했습니다.");
        }

        // 임시 이미지 URL
        String imageUrl = fileStore.save(image);

        feedImageRepository.save(
                FeedImage.builder()
                        .feedId(feed.getFeedId())
                        .userId(userId)
                        .imageUrl(imageUrl)
                        .sequence(sequence)
                        .takenTime(LocalDateTime.now())
                        .foodCategoryId(categoryId)
                        .build()
        );

        if (sequence == 9) {
            feed.setCompleted(true);
            feedRepository.save(feed);
        }
    }

    @Override
    public List<Feed> getUserFeeds(Long userId) {
        return feedRepository.findByUserId(userId);
    }

    @Override
    public List<FeedImage> getFeedImages(Long feedId) {
        return feedImageRepository.findByFeedIdOrderBySequenceAsc(feedId);
    }

    //전체 피드 (테스트용)

    public List<Feed> getAllFeeds() {
        return feedRepository.findAll();
    }


    // 메인 홈 피드

    public List<HomeFeedResponse> getHomeFeeds() {
        List<Feed> feeds =
                feedRepository.findByCompletedTrueOrderByFeedDateDesc();

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
                        .findByUserIdInAndCompletedTrueOrderByFeedDateDesc(userIds);

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