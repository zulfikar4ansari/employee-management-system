package com.ems.common.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

/**
 * Shared JSON serializer / deserializer
 * reused across microservices.
 */
public final class JsonUtils {

    private static final ObjectMapper MAPPER =
            new ObjectMapper().registerModule(new JavaTimeModule());

    private JsonUtils() {}

    public static String toJson(Object value) {
        try {
            return MAPPER.writeValueAsString(value);
        } catch (Exception ex) {
            throw new RuntimeException("JSON serialization failed", ex);
        }
    }

    public static <T> T fromJson(String json, Class<T> type) {
        try {
            return MAPPER.readValue(json, type);
        } catch (Exception ex) {
            throw new RuntimeException("JSON deserialization failed", ex);
        }
    }

    public static ObjectMapper mapper() {
        return MAPPER;
    }
}
