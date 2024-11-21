package com.example.myserver.exception;

import lombok.Getter;

public class PreconditionUnsatisfiedException extends RuntimeException {

    private static final long serialVersionUID = -300809562540443265L;

    @Getter
    private final Object detail;

    public PreconditionUnsatisfiedException(final String message) {
        super(message);
        this.detail = null;
    }

    public PreconditionUnsatisfiedException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }
}
