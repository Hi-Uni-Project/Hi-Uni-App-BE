package com.qoormthon.todool.domain.user.application.command;

import com.qoormthon.todool.domain.user.exception.UserInvalidValueException;
import com.qoormthon.todool.global.error.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SignUpUserCommand {
    private String userId;
    private String password;
    private String admissionYear;
    private String nickName;
    private String univName;
    private String univMail;
    private String firstMajor;
    private String secondMajor;
    private String mbti;
    private String imageUrl;

    public SignUpUserCommand(
            String userId,
            String password,
            String admissionYear,
            String nickName,
            String univName,
            String firstMajor,
            String secondMajor,
            String mbti,
            String univMail,
            String imageUrl
    ) {
        if (userId == null || userId.trim().isEmpty()
        || password == null || password.trim().isEmpty()
        || admissionYear == null || admissionYear.trim().isEmpty()
        || nickName == null || nickName.trim().isEmpty()
        || univName == null || univName.trim().isEmpty()
        || firstMajor == null || firstMajor.trim().isEmpty()
        || secondMajor == null || secondMajor.trim().isEmpty()
        || mbti == null || mbti.trim().isEmpty()
        || univMail == null || univMail.trim().isEmpty())
        {
            System.out.println("[SignUpUserCommand] 유효성 검사 실패");
            throw new UserInvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }

        this.userId = userId;
        this.password = password;
        this.admissionYear = admissionYear;
        this.nickName = nickName;
        this.univName = univName;
        this.univMail = univMail;
        this.firstMajor = firstMajor;
        this.secondMajor = secondMajor;
        this.mbti = mbti;
        this.imageUrl = null;
    }
}
