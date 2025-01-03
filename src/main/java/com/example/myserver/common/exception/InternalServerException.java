package com.example.myserver.common.exception;

import lombok.Getter;

public class InternalServerException extends RuntimeException {

    private static final long serialVersionUID = 2516238603253640950L;

    @Getter
    private final Object detail;

    public InternalServerException(final String message) {
        super(message);
        this.detail = null;
    }

    public InternalServerException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }

}
