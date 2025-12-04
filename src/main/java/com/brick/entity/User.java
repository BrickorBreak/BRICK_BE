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
    // 고민해야할점
    // 프론트에서 다 작성해야 통과 되는데 여기서 다 not null을 걸어야 하는거 아닌가 ? 하는 의문

    @Id
    private Long userId; // 솔하T가 만들어주는 PK ( 자동 생성 X )

    @Column(nullable = false, length = 50)
    private String realName; // 이름

    // unique걸어서 중복 방지
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
