package com.brick.repository;

import com.brick.entity.User;
import com.brick.entity.UserFoodPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFoodPreferenceRepository
        extends JpaRepository<UserFoodPreference, Long> {

    // 유저 기준 조회
    List<UserFoodPreference> findByUser(User user);

    // 음식 카테고리 기준 조회
    List<UserFoodPreference> findByFoodCategory_FoodCategoryId(Long foodCategoryId);
}
