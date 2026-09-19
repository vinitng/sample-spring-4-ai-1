package com.example.demo.dto.ui;

import java.time.OffsetDateTime;

public record UiResponse<T>(
        boolean success,
        String message,
        T data,
        OffsetDateTime timestamp
) {
    public static <T> UiResponse<T> ok(String message, T data) {
        return new UiResponse<>(true, message, data, OffsetDateTime.now());
    }

    public static <T> UiResponse<T> error(String message) {
        return new UiResponse<>(false, message, null, OffsetDateTime.now());
    }
}