package com.brick.repository;

import com.brick.entity.User;
import com.brick.entity.UserFoodPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FoodCategoryRepository extends JpaRepository<FoodCategoryRepository, Long> {

}
