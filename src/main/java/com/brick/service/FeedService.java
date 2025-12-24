    package com.brick.service;

    import com.brick.dto.HomeFeedResponse;
    import com.brick.entity.Feed;
    import com.brick.entity.FeedImage;
    import org.springframework.web.multipart.MultipartFile;

    import java.util.List;

    public interface FeedService {

        //  사진 저장 (카메라 → AI → 마이페이지)
        void saveImage(
                Long userId,
                MultipartFile image,
                Long categoryId,
                Double confidence
        );

        //  마이페이지용 피드 조회
        List<Feed> getUserFeeds(Long userId);

        // 특정 피드의 이미지 9장 조회
        List<FeedImage> getFeedImages(Long feedId);

        // 피드 생성/저장
        List<Feed> getAllFeeds();

        List<HomeFeedResponse> getHomeFeeds();

        List<HomeFeedResponse> getFeedsByFoodCategory(Long foodCategoryId);
    }