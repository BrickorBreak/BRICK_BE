package com.brick.repository;

import com.brick.entity.FeedImage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FeedImageRepository extends JpaRepository<FeedImage, Long> {

    List<FeedImage> findByFeedIdOrderBySequence(Long feedId);

    int countByFeedId(Long feedId);


}
