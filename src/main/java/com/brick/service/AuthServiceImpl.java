package com.brick.service;

import com.brick.dto.LoginRequestDto;
import com.brick.dto.SignUpRequestDto;
import com.brick.entity.AuthUser;
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

    private final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override
    public Long SingUp(SignUpRequestDto dto) {

        // ID 중복 체크
        authUserRepository.findById(dto.getId())
                .ifPresent(u -> {
                    throw new RuntimeException("이미 존재하는 ID입니다.");
                });

        // 전화번호 중복 체크
        authUserRepository.findByPhone(dto.getPhone())
                .ifPresent(u -> {
                    throw new RuntimeException("이미 존재하는 전화번호입니다.");
                });

        // AuthUser 저장
        AuthUser authUser = AuthUser.builder()
                .id(dto.getId())
                .pw(encoder.encode(dto.getPw()))
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .build();

        AuthUser savedAuthUser = authUserRepository.save(authUser);

        return savedAuthUser.getAuthUserId();
    }

    @Override
    public Long login(LoginRequestDto dto) {

        AuthUser authUser = authUserRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다."));

        if (!encoder.matches(dto.getPw(), authUser.getPw())) {
            throw new RuntimeException("비밀번호가 올바르지 않습니다.");
        }

        return authUser.getAuthUserId();
    }
}