

package com.brick.service;

import com.brick.entity.Feed;
import com.brick.entity.FeedImage;
import com.brick.repository.FeedImageRepository;
import com.brick.repository.FeedRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FeedServiceImpl implements FeedService {

    private final FeedRepository feedRepository;
    private final FeedImageRepository feedImageRepository;

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
        String imageUrl = "/uploads/temp.png";

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
}