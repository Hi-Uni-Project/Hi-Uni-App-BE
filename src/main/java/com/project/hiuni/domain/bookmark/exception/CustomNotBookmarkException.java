package com.project.hiuni.domain.bookmark.exception;

import com.project.hiuni.global.exception.CustomException;
import com.project.hiuni.global.exception.ErrorCode;

public class CustomNotBookmarkException extends CustomException {
    public CustomNotBookmarkException(ErrorCode errorCode) {
        super(errorCode);
    }
}
