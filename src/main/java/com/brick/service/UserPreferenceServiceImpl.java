package com.brick.service;

import com.brick.entity.FoodCategory;
import com.brick.entity.UserFoodPreference;
import com.brick.repository.FoodCategoryRepository;
import com.brick.repository.UserFoodPreferenceRepository;
import com.brick.repository.UserRepository;
import com.brick.dto.PreferenceResponseDto;
import com.brick.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserPreferenceServiceImpl implements UserPreferenceService {
    private final UserRepository userRepository;
    private final UserFoodPreferenceRepository preferenceRepository;
    private final FoodCategoryRepository foodCategoryRepository;

    // 유저가 취향으로 [ 1 , 3 , 7 ] 선택 가정

    @Override
    public void saveUserPreferences(Long userId, List<Long> foodIds) {
        User user = userRepository.findById(userId)
                .orElseGet(() -> userRepository.save(User.builder().userId(userId).build())); // 여기서 계속 터지는데 user테이블이 안만들어져서 발생함

        for(Long foodId : foodIds) {
        //  foodId가 1이면 FoodCategory( 1, "한식" ) 찾음
        FoodCategory category= foodCategoryRepository.findById(foodId)
                .orElseThrow(() -> new RuntimeException("음식 카테 고리 없음"));

                UserFoodPreference userFoodPreference = UserFoodPreference.builder()
                        .user(user)
                        .foodCategory(category)
                        .build();

                preferenceRepository.save(userFoodPreference);
        }
    }

    // 코드를 보기전
    // 먼저 테이블 구조를 보자!
    // user 테이블은
    // userId   name
    //  5       채연
    // FoodCategory 테이블
    // foodCategoryId   name
    //        1          한식
    //        3          일식
    // UserFoodPreference 테이블
    // id       userId      foodCategoryId
    // 10          5            1
    // 11          5            3
    // 12          5            7

    @Override
    public List<PreferenceResponseDto> getUserPreferences(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("유저 없음 : UserPreferenceService 확인하자"));

        // user와 다르게 userId 당 나올 수 있는 갯수가 여러개니까 list로 만들고 이를 저장
        List< UserFoodPreference> list = preferenceRepository.findByUser(user);

        List<PreferenceResponseDto> result = new ArrayList<>();

        // foodCategory가 (1 , '한식') 이렇게 저장되어있으니까 각각 id 와 name 꺼내는 것
        for(UserFoodPreference p : list){
            PreferenceResponseDto dto = PreferenceResponseDto.builder()
                    .foodId(p.getFoodCategory().getFoodCategoryId())
                    .name(p.getFoodCategory().getName())
                    .build();
            result.add(dto);
        }
        return result;
    }
}
