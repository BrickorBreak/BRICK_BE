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
    private Long userId; // 솔하T가 만들어주는 PK ( 자동 생성 X )

    @Column(nullable = false, unique = true, length = 50)
    private String realName; // 이름

    @Column(nullable = false, unique = true, length = 50)
    private String nickName; // 닉네임

    private Integer age; // 나이

    // 주소
    private String city;
    private String district;

    private String mbti; // mbti
    private String intro; // 소개글
    private String imageUrl; // 이미지
}
