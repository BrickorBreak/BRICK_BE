package com.brick.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "feed_images")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FeedImage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    @Column(nullable = false)
    private String imageUrl;

    @Column(nullable = false)
    private Integer sequence;

    @Column(nullable = false)
    private LocalDateTime takenTime;

    @Column(nullable = false)
    private Long feedId;

    @Column(nullable = false)
    private Long userId;

    private Long foodCategoryId;

}
