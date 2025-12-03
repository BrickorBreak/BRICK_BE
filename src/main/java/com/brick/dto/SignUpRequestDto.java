package com.brick.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignUpRequestDto {

    private String id;
    private String pw;
    private String phone;
    private String gender;

}
