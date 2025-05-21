package com.qoormthon.todool.domain.user.exception;

import com.qoormthon.todool.global.error.exception.CustomException;
import com.qoormthon.todool.global.error.exception.ErrorCode;

public class UserInvalidValueException extends CustomException {
    public UserInvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}
