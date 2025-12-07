package com.brick.repository;

import com.brick.entity.FoodCategory;
import com.brick.entity.User;
import com.brick.entity.UserFoodPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// JpaRepository<T, ID> 여기서 T는 엔티티 클래스 , ID는 키 타입
public interface FoodCategoryRepository extends JpaRepository<FoodCategory, Long> {

}
