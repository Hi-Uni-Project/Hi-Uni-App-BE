package com.qoormthon.todool.domain.user.adapter.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserFindResponseDto {
    private String userId;
    private String stdNo;
    private String nickName;
    private String univ;
    private String firstMajor;
    private String secondMajor;
    private String gender;
    private String mbti;
    private String imageUrl;
}
