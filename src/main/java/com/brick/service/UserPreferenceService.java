package com.brick.service;
import com.brick.dto.PreferenceResponseDto;
import com.brick.dto.UserResponseDto;

import java.util.List;


public interface UserPreferenceService {

    // 저장
    void saveUserPreferences(Long userId , List<Long> foodIds);

    // 조회 ( 이때 responseDto 는 id가 포함 X , but 파라미터로 들어옴 )
    List<PreferenceResponseDto> getUserPreferences(Long userId);


}
