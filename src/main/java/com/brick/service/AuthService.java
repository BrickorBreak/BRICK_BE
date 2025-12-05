package com.brick.service;

import com.brick.dto.LoginRequestDto;
import com.brick.dto.SignUpRequestDto;

public interface AuthService {

    Long login(LoginRequestDto dto);

    Long SingUp(SignUpRequestDto dto);
}
