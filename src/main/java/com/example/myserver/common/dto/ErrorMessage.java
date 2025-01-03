package com.example.myserver.common.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.util.Objects;

@Getter
public class ErrorMessage {

    private ErrorCode code;

    private String message;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object detail;

    public ErrorMessage() {
    }

    public ErrorMessage(final ErrorCode code, final String message) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(message);

        this.code = code;
        this.message = message;
        this.detail = null;
    }

    public ErrorMessage(final ErrorCode code, final String message, final Object detail) {
        Objects.requireNonNull(code);
        Objects.requireNonNull(message);

        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public enum ErrorCode {
        INTERNAL_SERVER_ERROR,
        PRECONDITION_FAILED,
        BAD_INPUT_PARAMETER,
        RESOURCE_NOT_FOUND,
        RESOURCE_ALREADY_EXIST,
        REQUEST_FORBIDDEN,
        CONTENT_ILLEGAL,
        NOT_ALLOW_NULL,
        UNAUTHORIZED,
        ES_EXCEPTION,
        EXCEED_ONLINE_USER_COUNT,
        EXCEED_REGISTER_USER_COUNT,

        NOT_PERMISSION;
    }
}
