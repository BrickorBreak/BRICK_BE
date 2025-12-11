package com.brick.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long commentId;

    // 댓글 : 피드 = N : 1 관계
    // LAZY는 성능 최적화 -> 현재 '댓글'만 필요한데 굳이 피드나 사용자 정보 가져올 필요 없기에
    // 그 반대는 EAGER
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "feed_id" , nullable = false )
    private Feed feed;

    // 댓글 : 사용자 = N : 1 관계
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    @Column(nullable = false , columnDefinition = "TEXT")
    private String content;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    // DB에 넣을 때 자동 생성
    @PrePersist
    public void prePersist(){
        this.createdAt = LocalDateTime.now();
    }
}
