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

@Service
@RequiredArgsConstructor
public class PhotoServiceImpl implements PhotoService {

    private final FeedRepository feedRepository;
    private final FeedImageRepository feedImageRepository;

    @Override
    public void savePhoto(
            Long userId,
            MultipartFile image,
            Long categoryId,
            Double confidence
    ) {

        // 오늘 날짜 피드 찾거나 생성
        Feed feed = feedRepository
                .findByUserIdAndFeedDate(userId, LocalDate.now())
                .orElseGet(() -> feedRepository.save(
                        Feed.builder()
                                .userId(userId)
                                .feedDate(LocalDate.now())
                                .isCompleted(false)
                                .build()
                ));

        // sequence 계산
        int sequence = feedImageRepository.countByFeedId(feed.getFeedId()) + 1;

        // FeedImage 생성
        FeedImage feedImage = FeedImage.builder()
                .feedId(feed.getFeedId())
                .userId(userId)
                .imageUrl("/uploads/temp.png") // 🔥 다음 단계에서 실제 저장
                .sequence(sequence)
                .takenTime(LocalDateTime.now())
                .foodCategoryId(categoryId)
                .build();

        feedImageRepository.save(feedImage);
    }
}
