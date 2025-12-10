package com.brick.repository;

import com.brick.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// 댓글 테이블에 접근하는 통로
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFeedId(Long feedId); // 피드 기준 조회 ( 기본 제공 crud에서 해주지 않아서 )
}
