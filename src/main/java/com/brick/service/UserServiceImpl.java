package com.brick.service;

import com.brick.common.FileStore;
import com.brick.dto.UserRequestDto;
import com.brick.dto.UserResponseDto;
import com.brick.entity.User;
import com.brick.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor // final 붙은 필드(userRepository)를 자동으로 생성자 주입
public class UserServiceImpl implements UserService{
    private final UserRepository userRepository;
    private final FileStore fileStore;

    @Transactional
    @Override
    // 사용자가 입력한 데이터를 id로 찾는다
    public void updateUser(Long userId, UserRequestDto dto ,MultipartFile image) {
        // userRepository(db의 창구)에서 id를 찾아 사용자가 입력한 데이터 (dto)를 user (entity) 실제 DB에 넣는다
        User user = userRepository.findById(userId)
                .orElseGet(() -> User.builder().userId(userId).build());
        // 닉네임 중복 체크
        if (dto.getNickName() != null &&
                userRepository.existsByNickNameAndUserIdNot(dto.getNickName(), userId)) {
            throw new RuntimeException("이미 사용 중인 닉네임입니다.");
        }

        // 이미지 저장
        if (image != null && !image.isEmpty()) {
            String imageUrl = fileStore.save(image);
            user.setImageUrl(imageUrl);
        }
        user.setRealName(dto.getRealName());
        user.setAge(dto.getAge());
        user.setCity(dto.getCity());
        user.setDistrict(dto.getDistrict());
        user.setNickName(dto.getNickName());
        user.setMbti(dto.getMbti());
        user.setIntro(dto.getIntro());

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
