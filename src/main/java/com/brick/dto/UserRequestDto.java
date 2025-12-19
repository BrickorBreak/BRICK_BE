package com.brick.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor @Builder
//  클라이언트가 보내는 데이터 -> 클라이언트와 다른 형식 방지 하기 위해
public class UserRequestDto {
    private String realName;   // 이름
    private Integer age;       // 나이
    private String city;       // 시
    private String district;   // 구
    private String nickName;   // 닉네임
    private String mbti;       // MBTI
    private String intro;      // 소개글
}
