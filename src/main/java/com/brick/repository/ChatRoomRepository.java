package com.brick.repository;

import com.brick.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom , Long> {
    // 누가 어떤 방에 들어가 있지 ?
    @Query("""
        SELECT m.room
        FROM ChatRoomMember m
        WHERE m.user.userId IN :userIds
        GROUP BY m.room
        HAVING COUNT(DISTINCT m.user.userId) = 2
    """)

    // Optional -> 결과가 있을 수도 있고 , 없을 수도 있음을 표시 하는 상자
    // 두명이 이미 같이 있는 1:1 방이 있으면 찾아줘 -> 철수와 영희 시작할때마다 방 만드니까
    Optional<ChatRoom> findPrivateRoomByUserIds(@Param("userIds") List<Long> userIds);

}
