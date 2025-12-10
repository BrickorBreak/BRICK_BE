package com.brick.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "feeds")
@Getter @Setter
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
    private LocalDate feedDate; // 하루 기준 날짜

    @Column(nullable = false)
    private Boolean isCompleted; //4시에 다 채워졌는지 안 채워졌는지 확인
    
    private LocalDateTime createdAt;
}

