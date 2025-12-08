package com.brick.repository;

import com.brick.entity.Feed;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface FeedRepository extends JpaRepository<Feed, Long> {

    Optional<Feed> findByUserIdAndFeedDate(Long userId, LocalDate feedDate);

    List<Feed> findByFeedDate(LocalDate feedDate);

    List<Feed> findByUserId(Long userId);

    List<Feed> findByIsCompletedFalse();

}
