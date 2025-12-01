package com.brick.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
public class UserResponseDto {

    private Long userId;
    private String nickname;
    private String mbti;
    private String intro;
    private Integer age;
    private String city;
    private String district;
    private String imageUrl;
}
