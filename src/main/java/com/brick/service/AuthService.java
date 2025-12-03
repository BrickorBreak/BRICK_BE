package com.brick.service;

import com.brick.dto.LoginRequestDto;
import com.brick.dto.SignUpRequestDto;

public interface AuthService {

    Long Signup(SignUpRequestDto dto);

    Long login(LoginRequestDto dto);
}
