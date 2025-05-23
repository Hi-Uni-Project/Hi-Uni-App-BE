package com.qoormthon.todool.domain.user.exception;

import com.qoormthon.todool.global.error.exception.CustomException;
import com.qoormthon.todool.global.error.exception.ErrorCode;

public class InvalidCredentialsException extends CustomException {
    public InvalidCredentialsException(ErrorCode errorCode) {
        super(errorCode);
    }
}
