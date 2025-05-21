package com.qoormthon.todool.domain.user.mapper;

import com.qoormthon.todool.domain.user.domain.entity.UserEntity;
import com.qoormthon.todool.domain.user.domain.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User UserEntityToUser(UserEntity userEntity) {
        return User.builder()
                .userId(userEntity.getUserId())
                .stdNo(userEntity.getStdNo())
                .password(userEntity.getPassword())
                .nickName(userEntity.getNickName())
                .univName(userEntity.getUnivName())
                .firstMajor(userEntity.getFirstMajor())
                .secondMajor(userEntity.getSecondMajor())
                .mbti(userEntity.getMbti())
                .userInterests(null)
                .imageUrl(userEntity.getImageUrl())
                .build();
    }

//    public UserFindResponseDto userToUserFindResponseDto(User user) {
//        return UserFindResponseDto.builder()
//                .userId(user.getUserId())
//                .stdNo(user.getStdNo())
//                .nickName(user.getNickName())
//                .univ(user.getUniv())
//                .firstMajor(user.getFirstMajor())
//                .secondMajor(user.getSecondMajor())
//                .mbti(user.getMbti())
//                .imageUrl(user.getImageUrl())
//                .build();
//    }
}
