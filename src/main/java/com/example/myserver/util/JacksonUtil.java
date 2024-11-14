package com.example.myserver.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rzon.exam.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Objects;

@Slf4j
public final class JacksonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    private JacksonUtil() { }

    public static String serialize(final Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            log.warn("json serialize error", e);
            throw new ValidationException("json serialize error", e);
        }
    }

    public static <T> T deserialize(final String value, final TypeReference<T> valueTypeRef) {
        Objects.requireNonNull(value);
        try {
            return objectMapper.readValue(value, valueTypeRef);
        } catch (IOException e) {
            log.warn("json deserialize error", e);
            throw new ValidationException("json serialize error", e);
        }
    }

    public static <T> T deserialize(final String value, final Class<T> valueType) {
        Objects.requireNonNull(value);
        try {
            return objectMapper.readValue(value, valueType);
        } catch (IOException e) {
            log.warn("json deserialize error", e);
            throw new ValidationException("json serialize error", e);
        }
    }
}
