package com.brick.service;

import com.brick.dto.UserResponseDto;
import com.brick.entity.User;
import com.brick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    public UserResponseDto getUser(Long userId) {
        // 해당 유저를 조회 한후에 DTO로 변환해 반환함
        // 예를 들어 값이 있으면 객체 꺼내고 아니면 에러 던져버림
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return UserResponseDto.builder()
                .userId(user.getUserId())
                .nickname(user.getNickname())
                .intro(user.getIntro())
                .mbti(user.getMbti())
                .city(user.getCity())
                .district(user.getDistrict())
                .build();

    }
}
