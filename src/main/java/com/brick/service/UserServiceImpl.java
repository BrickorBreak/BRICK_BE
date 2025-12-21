package com.brick.service;

import com.brick.dto.UserRequestDto;
import com.brick.dto.UserResponseDto;
import com.brick.entity.User;
import com.brick.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor // final 붙은 필드(userRepository)를 자동으로 생성자 주입
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;

    @Override
    // 사용자가 입력한 데이터를 id로 찾는다
    public void updateUser(Long userId, UserRequestDto dto) {
        // userRepository(db의 창구)에서 id를 찾아 사용자가 입력한 데이터 (dto)를 user (entity) 실제 DB에 넣는다
        User user = userRepository.findById(userId)
                .orElseThrow(()-> new RuntimeException("User not  found"));
        user.setRealName(dto.getRealName());
        user.setAge(dto.getAge());
        user.setCity(dto.getCity());
        user.setDistrict(dto.getDistrict());
        user.setNickName(dto.getNickName());
        user.setMbti(dto.getMbti());
        user.setIntro(dto.getIntro());
        user.setImageUrl(dto.getImageUrl());

        // 저장해야 됨
        userRepository.save(user);

    }

    @Override
    public UserResponseDto getUser(Long userId) {
        // 해당 유저를 조회 한후에 DTO로 변환해 반환함
        // 예를 들어 값이 있으면 객체 꺼내고 아니면 에러 던져버림
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // User 엔티티 → UserResponseDto로 변환하는 코드 , 그냥 user던지면 보안 위험
        return UserResponseDto.builder()
                .userId(user.getUserId())
                .realName(user.getRealName())
                .age(user.getAge())
                .city(user.getCity())
                .district(user.getDistrict())
                .nickName(user.getNickName())
                .mbti(user.getMbti())
                .intro(user.getIntro())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
