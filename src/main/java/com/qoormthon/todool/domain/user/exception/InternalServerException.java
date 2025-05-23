package com.qoormthon.todool.domain.user.exception;

import com.qoormthon.todool.global.error.exception.CustomException;
import com.qoormthon.todool.global.error.exception.ErrorCode;

public class InternalServerException extends CustomException {
    public InternalServerException(ErrorCode errorCode) {
        super(errorCode);
    }
}
