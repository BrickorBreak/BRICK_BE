package com.brick.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequestDto {

    private String id;
    private String pw;
}
//로그인 때는 이 두개만 필요 !