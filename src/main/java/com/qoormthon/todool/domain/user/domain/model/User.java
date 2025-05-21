package com.qoormthon.todool.domain.user.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class User {

    //식별자
    private final String userId;

    //속성 필드
    private String stdNo;
    private String password;
    private String nickName;
    private String univName;
    private String firstMajor;
    private String secondMajor;
    private String mbti;
    private List<Long> userInterests;
    private String imageUrl;
}
