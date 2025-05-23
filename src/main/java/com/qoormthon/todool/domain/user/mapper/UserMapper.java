package com.qoormthon.todool.domain.user.mapper;

import com.qoormthon.todool.domain.user.adapter.dto.request.LoginUserRequestDto;
import com.qoormthon.todool.domain.user.adapter.dto.request.SignUpUserRequestDto;
import com.qoormthon.todool.domain.user.adapter.dto.response.FindUserResponseDto;
import com.qoormthon.todool.domain.user.application.command.LoginUserCommand;
import com.qoormthon.todool.domain.user.application.command.SignUpUserCommand;
import com.qoormthon.todool.domain.user.domain.entity.UserEntity;
import com.qoormthon.todool.domain.user.domain.model.User;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UserMapper {

    public User UserEntityToUser(UserEntity userEntity, List<Long> userInterestIdList) {
        return User.builder()
                .userId(userEntity.getUserId())
                .admissionYear(userEntity.getAdmissionYear())
                .password(userEntity.getPassword())
                .nickName(userEntity.getNickName())
                .univName(userEntity.getUnivName())
                .firstMajor(userEntity.getFirstMajor())
                .secondMajor(userEntity.getSecondMajor())
                .mbti(userEntity.getMbti())
                .userInterests(userInterestIdList)
                .imageUrl(userEntity.getImageUrl())
                .build();
    }

    public UserEntity UserToUserEntity(User user) {
        return UserEntity.builder()
                .userId(user.getUserId())
                .password(user.getPassword())
                .admissionYear(user.getAdmissionYear())
                .nickName(user.getNickName())
                .univMail(user.getUnivName())
                .univName(user.getUnivName())
                .univMail(user.getUnivMail())
                .firstMajor(user.getFirstMajor())
                .secondMajor(user.getSecondMajor())
                .mbti(user.getMbti())
                .imageUrl(user.getImageUrl())
                .build();
    }

    public LoginUserCommand loginUserRequestDtoToCommand(LoginUserRequestDto loginUserRequestDto) {
        return LoginUserCommand.builder()
                .userId(loginUserRequestDto.getUserId())
                .password(loginUserRequestDto.getPassword())
                .build();
    }

    public SignUpUserCommand signUpUserRequestDtoToCommand(SignUpUserRequestDto signUpUserRequestDto) {
        return SignUpUserCommand.builder()
                .userId(signUpUserRequestDto.getUserId())
                .password(signUpUserRequestDto.getPassword())
                .admissionYear(signUpUserRequestDto.getAdmissionYear())
                .nickName(signUpUserRequestDto.getNickName())
                .univName(signUpUserRequestDto.getUnivName())
                .univMail(signUpUserRequestDto.getUnivMail())
                .firstMajor(signUpUserRequestDto.getFirstMajor())
                .secondMajor(signUpUserRequestDto.getSecondMajor())
                .mbti(signUpUserRequestDto.getMbti())
                .build();
    }

    public User signUpUserCommandToUser(SignUpUserCommand signUpUserCommand) {
        return User.builder()
                .userId(signUpUserCommand.getUserId())
                .password(signUpUserCommand.getPassword())
                .admissionYear(signUpUserCommand.getAdmissionYear())
                .nickName(signUpUserCommand.getNickName())
                .univName(signUpUserCommand.getUnivName())
                .univMail(signUpUserCommand.getUnivMail())
                .firstMajor(signUpUserCommand.getFirstMajor())
                .secondMajor(signUpUserCommand.getSecondMajor())
                .mbti(signUpUserCommand.getMbti())
                .imageUrl(signUpUserCommand.getImageUrl())
                .build();
    }

    public FindUserResponseDto userToFindUserResponseDto(User user) {
        return FindUserResponseDto.builder()
                .userId(user.getUserId())
                .admissionYear(user.getAdmissionYear())
                .nickName(user.getNickName())
                .univName(user.getUnivName())
                .firstMajor(user.getFirstMajor())
                .secondMajor(user.getSecondMajor())
                .mbti(user.getMbti())
                .userInterests(user.getUserInterests())
                .imageUrl(user.getImageUrl())
                .build();
    }
}
