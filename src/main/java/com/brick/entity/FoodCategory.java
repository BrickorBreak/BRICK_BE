package com.brick.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
@Builder
public class FoodCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long foodCategoryId;

    @Column(nullable = false) // 한식 , 양식 , 일식 구분
    private String name;
}
