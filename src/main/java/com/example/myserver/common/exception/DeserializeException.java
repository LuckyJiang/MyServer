package com.example.myserver.common.exception;

import lombok.Getter;

public class DeserializeException extends RuntimeException {

    @Getter
    private final Object detail;

    public DeserializeException(final String message) {
        super(message);
        this.detail = null;
    }

    public DeserializeException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }
}
