package com.brick.service;

import com.brick.dto.LoginRequestDto;
import com.brick.dto.SignUpRequestDto;
import com.brick.entity.User;
import com.brick.repository.AuthUserRepository;
import com.brick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthUserRepository authUserRepository;
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();



}

