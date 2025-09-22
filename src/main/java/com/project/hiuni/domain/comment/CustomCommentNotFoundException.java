package com.project.hiuni.domain.comment;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomCommentNotFoundException extends CustomException {
    public CustomCommentNotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }
}
