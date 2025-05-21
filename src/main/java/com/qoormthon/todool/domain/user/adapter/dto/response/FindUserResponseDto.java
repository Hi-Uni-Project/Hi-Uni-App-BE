package com.qoormthon.todool.domain.user.adapter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FindUserResponseDto {
    private String userId;
    private String stdNo;
    private String nickName;
    private String univName;
    private String firstMajor;
    private String secondMajor;
    private String mbti;
    private List<Long> userInterests;
    private String imageUrl;
}
