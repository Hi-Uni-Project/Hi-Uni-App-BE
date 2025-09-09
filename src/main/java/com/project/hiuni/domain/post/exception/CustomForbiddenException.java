package com.project.hiuni.domain.post.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomForbiddenException extends CustomException {
    public CustomForbiddenException(ErrorCode errorCode) {
        super(errorCode);
    }
}
