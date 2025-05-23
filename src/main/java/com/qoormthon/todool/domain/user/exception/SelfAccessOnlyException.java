package com.qoormthon.todool.domain.user.exception;

import com.qoormthon.todool.global.error.exception.CustomException;
import com.qoormthon.todool.global.error.exception.ErrorCode;

public class SelfAccessOnlyException extends CustomException {
    public SelfAccessOnlyException(ErrorCode errorCode) {
        super(errorCode);
    }
}
