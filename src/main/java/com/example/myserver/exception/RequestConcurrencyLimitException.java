package com.example.myserver.exception;

import lombok.Getter;

public class RequestConcurrencyLimitException extends RuntimeException {

    private static final long serialVersionUID = -641036556214029529L;

    @Getter
    private final Object detail;

    public RequestConcurrencyLimitException(final String message) {
        super(message);
        this.detail = null;
    }

    public RequestConcurrencyLimitException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }

}
