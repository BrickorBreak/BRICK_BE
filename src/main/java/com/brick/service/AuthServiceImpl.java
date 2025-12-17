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

        //ID 중복 체크하기 !
        authUserRepository.findById(dto.getId())
                .ifPresent(u -> {
                    throw new RuntimeException("이미 존재하는 ID입니다.");
                });

        //Phone도 중복 체크 !
        authUserRepository.findByPhone(dto.getPhone())
                .ifPresent(u -> {
                    throw new RuntimeException("이미 존재하는 전화번호입니다.");
                });

        //AuthUser 먼저 저장 (userId 자동 생성) !
        AuthUser authUser = AuthUser.builder()
                .id(dto.getId())
                .pw(encoder.encode(dto.getPw()))
                .phone(dto.getPhone())
                .gender(dto.getGender())
                .build();

        AuthUser savedAuthUser = authUserRepository.save(authUser);

        //여기서 바로 DB가 userId 자동으로 만들어줌 !
        Long newUserId = savedAuthUser.getUserId();

        //User 테이블에 기본 유저 생성 !
        User user = User.builder()
                .userId(newUserId)
                .realName("미입력")
                .nickName("미입력")
                .build();

        userRepository.save(user);

        //채연이가 사용할 userId 드디어 반환 !
        return newUserId;

    }

    @Override
    public Long login(LoginRequestDto dto) {

        AuthUser authUser = authUserRepository.findById(dto.getId())
                .orElseThrow(() -> new RuntimeException("존재하지 않는 ID입니다."));

        if (!encoder.matches(dto.getPw(), authUser.getPw())) {
            throw new RuntimeException("비밀번호가 올바르지 않습니다.");
        }

        return authUser.getUserId();
    }

}

