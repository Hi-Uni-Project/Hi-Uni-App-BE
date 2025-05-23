package com.qoormthon.todool.domain.user.exception;

import com.qoormthon.todool.global.error.exception.CustomException;
import com.qoormthon.todool.global.error.exception.ErrorCode;

public class UserDuplicatedException extends CustomException {
    public UserDuplicatedException(ErrorCode errorCode) {
        super(errorCode);
    }
}
