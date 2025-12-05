package com.brick.repository;

import com.brick.entity.User;
import com.brick.entity.UserFoodPreference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserFoodPreferenceRepository extends JpaRepository<UserFoodPreference, Long> {
    List<UserFoodPreference> findByUser(User user);
}
