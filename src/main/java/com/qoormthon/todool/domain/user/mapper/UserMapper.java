package com.qoormthon.todool.domain.user.mapper;

import com.qoormthon.todool.domain.user.adapter.dto.response.UserFindResponseDto;
import com.qoormthon.todool.domain.user.domain.entity.UserEntity;
import com.qoormthon.todool.domain.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User UserEntityToUser(UserEntity userEntity) {
        return User.createUser(userEntity.getUserId(), userEntity.getStdNo(), userEntity.getPassword()
        , userEntity.getNickName(), userEntity.getUniv()
        , userEntity.getFirstMajor(), userEntity.getSecondMajor()
        , userEntity.getMbti(), userEntity.getImageUrl());
    }

    public UserFindResponseDto userToUserFindResponseDto(User user) {
        return UserFindResponseDto.builder()
                .userId(user.getUserId())
                .stdNo(user.getStdNo())
                .nickName(user.getNickName())
                .univ(user.getUniv())
                .firstMajor(user.getFirstMajor())
                .secondMajor(user.getSecondMajor())
                .mbti(user.getMbti())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
