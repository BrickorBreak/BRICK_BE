package com.brick.dto;

// 롬복은 자동으로 getter/setter, 생성자, builder
import lombok.*;


@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

// 서버가 클라이언트에게 보내는 값
// 클라이언트에게 보내는 값이기에 보안성 중요 !
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
