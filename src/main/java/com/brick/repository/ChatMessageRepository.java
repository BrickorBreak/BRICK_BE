package com.brick.repository;

import com.brick.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {
    List<ChatMessage> findByRoom_RoomIdOrderByCreatedAtAsc(Long roomId); // 오래된 -> 최신순 ( 오름차순으로 정렬 ) 메세지 뽑기

    Optional<ChatMessage> findTopByRoom_RoomIdOrderByCreatedAtDesc(Long roomId);

}
