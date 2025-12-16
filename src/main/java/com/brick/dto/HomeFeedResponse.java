package com.brick.dto;

import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@AllArgsConstructor
public class HomeFeedResponse {

    private Long feedId;
    private LocalDate feedDate;

    private Long userId;
    private String nickName;
    private String imageUrl;
    private Integer birth;
    private String city;
    private String district;
    private String intro;

    private List<FeedImageDto> images;
}
