package com.aiplus.aiplus.exceptions;

import lombok.Getter;

@Getter
public class InvalidTokenException extends RuntimeException {

    private int errorCode;

    public InvalidTokenException(String message, int errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
