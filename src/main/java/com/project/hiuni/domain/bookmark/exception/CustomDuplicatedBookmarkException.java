package com.project.hiuni.domain.bookmark.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomDuplicatedBookmarkException extends CustomException {
    public CustomDuplicatedBookmarkException(ErrorCode errorCode) {
        super(errorCode);
    }
}
