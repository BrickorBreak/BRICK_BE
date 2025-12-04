package com.brick.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
// 사용자 to 서버
public class PreferenceRequestDto {
    private List<Long> preferences;
}
