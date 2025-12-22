package com.brick.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table( // 101번 철수 있으면 또 101번 철수 추가 못함
        uniqueConstraints = @UniqueConstraint(columnNames = {"room_id","user_id"})
)

// 참가자 명단 ( 방에 누가 있는지 )
public class ChatRoomMember {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id" , nullable = false)
    private ChatRoom room;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id" , nullable = false)
    private User user;

    public ChatRoomMember(ChatRoom room , User user){
        this.room = room;
        this.user = user;
    }
}
