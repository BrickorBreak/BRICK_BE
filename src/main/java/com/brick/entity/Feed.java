package com.brick.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "feeds")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Feed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long feedId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private LocalDate feedDate;

    @Column(name = "is_completed", nullable = false)
    private Boolean completed;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}