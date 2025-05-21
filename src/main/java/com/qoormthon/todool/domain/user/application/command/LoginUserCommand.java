package com.qoormthon.todool.domain.user.application.command;

import com.qoormthon.todool.domain.user.exception.UserInvalidValueException;
import com.qoormthon.todool.global.error.exception.ErrorCode;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class LoginUserCommand {
    private final String userId;
    private final String password;

    public LoginUserCommand(String userId, String password) {
        if (userId == null || userId.trim().isEmpty()) {
            throw new UserInvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserInvalidValueException(ErrorCode.INVALID_INPUT_VALUE);
        }
        this.userId = userId;
        this.password = password;
    }
}
