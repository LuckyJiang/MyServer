package com.example.myserver.util;

import com.example.myserver.common.exception.DeserializeException;
import com.example.myserver.common.exception.InternalServerException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Objects;

public final class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    static {
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }

    private JacksonUtil() {
    }

    public static String serialize(final Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new InternalServerException("json serialize error", e);
        }
    }

    public static <T> T deserialize(final String value, final TypeReference<T> valueTypeRef) {
        Objects.requireNonNull(value);
        try {

            return OBJECT_MAPPER.readValue(value, valueTypeRef);
        } catch (IOException e) {
            throw new DeserializeException(value, e);
        }
    }

    public static <T> T deserialize(final String value, final Class<T> valueType) {
        Objects.requireNonNull(value);
        try {
            return OBJECT_MAPPER.readValue(value, valueType);
        } catch (IOException e) {
            throw new DeserializeException(value, e);
        }
    }

}
