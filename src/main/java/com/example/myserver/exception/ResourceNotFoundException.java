package com.example.myserver.exception;

import lombok.Getter;

public class ResourceNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 8454625959503126134L;

    @Getter
    private final Object detail;

    public ResourceNotFoundException(final String message) {
        super(message);
        this.detail = null;
    }

    public ResourceNotFoundException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }
}
