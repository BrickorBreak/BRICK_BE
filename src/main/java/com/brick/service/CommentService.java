package com.brick.service;

import com.brick.dto.CommentRequestDto;
import com.brick.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {
    // 특정 피드에 댓글 작성 기능
    CommentResponseDto create(Long feedId , CommentRequestDto dto);
    List<CommentResponseDto> getComments(Long feedId);
    void delete(Long commentId);
}
