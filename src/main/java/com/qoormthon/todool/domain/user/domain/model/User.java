package com.qoormthon.todool.domain.user.domain.model;

import lombok.Getter;

@Getter
public class User {

    //식별자
    private final String userId;

    //속성 필드
    private String stdNo;
    private String password;
    private String nickName;
    private String univ;
    private String firstMajor;
    private String secondMajor;
    private String mbti;
    private String imageUrl;

    private User(String userId, String stdNo, String password, String nickName
    , String univ, String firstMajor, String secondMajor
    , String mbti, String imageUrl) {
        this.userId = userId;
        this.stdNo = stdNo;
        this.password = password;
        this.nickName = nickName;
        this.univ = univ;
        this.firstMajor = firstMajor;
        this.secondMajor = secondMajor;
        this.mbti = mbti;
        this.imageUrl = imageUrl;
    }

    public static User createUser(String userId, String stdNo, String password, String nickName
            , String univ, String firstMajor, String secondMajor
            , String mbti, String imageUrl) {
        return new User(userId, stdNo, password, nickName
                , univ, firstMajor, secondMajor
                , mbti, imageUrl);
    }
}
