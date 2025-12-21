package com.brick.service;

import com.brick.dto.CommentRequestDto;
import com.brick.dto.CommentResponseDto;
import com.brick.entity.Comment;
import com.brick.entity.Feed;
import com.brick.entity.User;
import com.brick.repository.CommentRepository;
import com.brick.repository.FeedRepository;
import com.brick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService{
    private final CommentRepository commentRepository;
    private final FeedRepository feedRepository;
    private final UserRepository userRepository;

    // 댓글 생성
    @Override
    public CommentResponseDto create(Long userId, Long feedId, CommentRequestDto dto) {
       Feed feed = feedRepository.findById(feedId) // /api/v1/feeds/10/comments url에서 가져옴 -> feedId 써도 됨
               .orElseThrow(() -> new RuntimeException("피드가 없음"));

       User user = userRepository.findById(userId) // request로 들어오니까 dto에서 꺼내써야됨
               .orElseThrow(() -> new RuntimeException("유저가 없음"));

       // 객체 만드는 방법 중 하나 builder
        // 자바에서는 객체로 만들고 DB에서는 id로 저장
       Comment comment = Comment.builder()
               .feed(feed)
               .user(user)
               .content(dto.getContent())
               .build();

       commentRepository.save(comment); // save는 INSERT문 ! -> 따라서 바로 DB 저장

       return toDto(comment); // 프론트가 원하는 값을 받을 수 있도록 정제
    }

    // 댓글 조회
    private CommentResponseDto toDto(Comment comment) {
        return CommentResponseDto.builder()
                .commentId(comment.getCommentId())
                .userId(comment.getUser().getUserId()) // user참조 하니까 user에서 가져와야됨
                .nickname(comment.getUser().getNickName())
                .content(comment.getContent())
                .createdAt(comment.getCreatedAt())
                .build();
    }

    @Override
    public List<CommentResponseDto> getComments(Long feedId) {
        feedRepository.findById(feedId)
                .orElseThrow(() -> new RuntimeException("피드 없음"));

        // 피드아이디에 연결된 모든 댓글을 DB에서 가져옴 -> 그 결과 List<Comment> 형태
        List<Comment> comments = commentRepository.findByFeed_FeedId(feedId);

        // Comment객체를 프론트에 보내줄 DTO 리스트 (result)를 하나 만들어두는 단계
        // 객체를 프론트에 그대로 전달 할 수 없으니까
        List<CommentResponseDto> result = new ArrayList<>();

        for(Comment comment : comments){
            result.add(toDto(comment));
        }
        return result;
    }

    // 댓글 삭제
    @Override
    public void delete(Long commentId) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("댓글 없음"));

        commentRepository.delete(comment);
    }
}
