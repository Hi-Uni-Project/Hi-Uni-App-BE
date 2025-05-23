package com.qoormthon.todool.domain.user.exception;

import com.qoormthon.todool.domain.user.application.service.CheckUserService;
import com.qoormthon.todool.global.error.exception.CustomException;
import com.qoormthon.todool.global.error.exception.ErrorCode;

public class InvalidFileTypeException extends CustomException {
    public InvalidFileTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
