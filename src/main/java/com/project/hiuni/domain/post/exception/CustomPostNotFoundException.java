package com.project.hiuni.domain.post.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomPostNotFoundException extends CustomException {
    public CustomPostNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
