package com.brick.entity;

import jakarta.persistence.*;   // ★ JPA 어노테이션들 import
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  //
    private Long userId;

    @Column(nullable = false, unique = true, length = 50)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, unique = true, length = 50)
    private String nickname;

    private String mbti;
    private String intro;
    private String city;
    private String district;
    private String imageUrl;
}
