package com.project.hiuni.domain.post.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomDuplicatedLikeException extends CustomException {
    public CustomDuplicatedLikeException(ErrorCode errorCode) {
        super(errorCode);
    }
}
