package com.brick.controller;

import com.brick.dto.CommentRequestDto;
import com.brick.dto.CommentResponseDto;
import com.brick.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class CommentController {
    private final CommentService commentService;

        @PostMapping("/feeds/{feedId}/comments")
        public ResponseEntity<CommentResponseDto> createComment(
                @PathVariable Long feedId,
                @RequestBody CommentRequestDto dto
        ){
            CommentResponseDto response = commentService.create(feedId,dto);
            return ResponseEntity.ok(response);
        }

        @GetMapping("/feeds/{feedId}/comments")
        public ResponseEntity<List<CommentResponseDto>> getComments(@PathVariable Long feedId){
            List<CommentResponseDto> comments = commentService.getComments(feedId);
            return ResponseEntity.ok(comments);
        }

        @DeleteMapping("/comments/{commentId}")
        public ResponseEntity<Void> deleteComment(@PathVariable Long commentId){
            commentService.delete(commentId);
            return ResponseEntity.noContent().build(); // 요청 성공 했는데 보낼 body 없음
        }

}
