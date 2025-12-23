package com.brick.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatRoom {
    // 단톡방 ( 방 번호 , 방 만든 시간 )

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Long roomId;

    private LocalDateTime createdAt = LocalDateTime.now();

}
