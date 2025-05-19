package com.qoormthon.todool.domain.user.exception;

public class UserInvalidValueException extends RuntimeException {
    public UserInvalidValueException(String message) {
        super(message);
    }
}
