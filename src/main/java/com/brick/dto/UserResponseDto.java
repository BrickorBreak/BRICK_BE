package com.brick.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 서버가 클라이언트에게 보내는 값

public class UserResponseDto {
    private Long userId;
    private String realName;
    private Integer age;
    private String city;
    private String district;
    private String nickName;
    private String mbti;
    private String intro;
    private String imageUrl;
}
