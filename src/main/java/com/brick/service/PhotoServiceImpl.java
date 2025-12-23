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

        // 오늘 날짜 feed 찾기 or 생성
        Feed feed = feedRepository
                .findByUserIdAndFeedDate(userId, LocalDate.now())
                .orElseGet(() -> feedRepository.save(
                        Feed.builder()
                                .userId(userId)
                                .feedDate(LocalDate.now())
                                .completed(false)
                                .createdAt(LocalDateTime.now())
                                .build()
                ));

        // 현재 이미지 개수
        int currentCount = feedImageRepository.countByFeedId(feed.getFeedId());

        if (currentCount >= 9) {
            throw new RuntimeException("이미 9장 모두 저장되었습니다.");
        }

        int sequence = currentCount + 1;

        // 이미지 URL (지금은 임시)
        String imageUrl = "https://picsum.photos/600?random=" + System.currentTimeMillis() + ".png";

        // FeedImage 저장
        FeedImage feedImage = FeedImage.builder()
                .feedId(feed.getFeedId())
                .userId(userId)
                .imageUrl(imageUrl)
                .sequence(sequence)
                .takenTime(LocalDateTime.now())
                .foodCategoryId(categoryId)
                .build();

        feedImageRepository.save(feedImage);

        // 9장 다 찼으면 feed 완료 처리
        if (sequence == 9) {
            feed.setCompleted(true);
            feedRepository.save(feed);
        }
    }
}
