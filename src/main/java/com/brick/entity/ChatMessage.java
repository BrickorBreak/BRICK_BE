package com.brick.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
public class ChatMessage {
    // 메세지
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long messageId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id", nullable = false)
    private ChatRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id" , nullable = false)
    private User sender;

    @Column(nullable = false , length = 1000)
    private String content;

    private LocalDateTime createdAt;

    public ChatMessage(ChatRoom room, User sender , String content){
        this.room = room;
        this.sender = sender;
        this.content = content;
    }
}
