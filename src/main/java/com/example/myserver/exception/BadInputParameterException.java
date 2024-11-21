package com.example.myserver.exception;

import lombok.Getter;

public class BadInputParameterException extends RuntimeException {

    private static final long serialVersionUID = -5551941263982064710L;

    @Getter
    private final Object detail;

    public BadInputParameterException(final String message) {
        super(message);
        this.detail = null;
    }

    public BadInputParameterException(final String message, final Object detail) {
        super(message);
        this.detail = detail;
    }

}
