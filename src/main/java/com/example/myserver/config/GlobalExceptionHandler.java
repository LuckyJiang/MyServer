package com.example.myserver.config;

import com.example.myserver.common.dto.ErrorMessage;
import com.example.myserver.common.exception.BadInputParameterException;
import com.example.myserver.common.exception.InternalServerException;
import com.example.myserver.common.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpServerErrorException;


@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage format(final ValidationException e) {
        return new ErrorMessage(ErrorMessage.ErrorCode.BAD_INPUT_PARAMETER, e.getMessage(), e.getDetail());
    }

    @ExceptionHandler(BadInputParameterException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorMessage format(final BadInputParameterException e) {
        return new ErrorMessage(ErrorMessage.ErrorCode.BAD_INPUT_PARAMETER, e.getMessage());
    }

    @ExceptionHandler(HttpServerErrorException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage format(final HttpServerErrorException e) {
        log.error("依赖服务内部异常", e.getResponseBodyAsString());
        return new ErrorMessage(ErrorMessage.ErrorCode.INTERNAL_SERVER_ERROR, "依赖服务内部异常");
    }

    @ExceptionHandler(InternalServerException.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorMessage format(final InternalServerException e) {
        log.error("服务内部异常", e);
        return new ErrorMessage(ErrorMessage.ErrorCode.INTERNAL_SERVER_ERROR, "服务器内部异常");
    }

}
