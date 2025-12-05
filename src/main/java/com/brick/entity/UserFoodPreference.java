package com.brick.entity;
// 사용자가 어떤 음식 카테 고리 원하는지 선택 테이블

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class UserFoodPreference {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY) // 자동증가
    private Long id;

    // 유저 한명은 여러 유저의 취향과 연결
    @ManyToOne // 여러 취향(UserFoodPreference) : 유저(User) = Many : One
    @JoinColumn(name = "userId")
    private User user;

    // 음식 카테고리(일식 : 2번 )은 여러 유저의 취향 ( 10 , 11 , 12번 user)와 연결
    @ManyToOne // 여러 취향(UserFoodPreference) : 음식 카테고리(FoodCategory) = Many : One
    @JoinColumn(name = "foodCategoryId")
    private FoodCategory foodCategory;
}
