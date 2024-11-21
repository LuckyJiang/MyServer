package com.example.myserver.exception;

import lombok.Getter;

public class ValidationException extends RuntimeException {

    private static final long serialVersionUID = -8465423848306076988L;

    @Getter
    private final Object detail;

    public ValidationException(final String message) {
        super(message);
        this.detail = null;
    }

    public ValidationException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }

}
