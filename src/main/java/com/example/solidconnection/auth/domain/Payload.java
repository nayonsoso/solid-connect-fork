package com.example.solidconnection.auth.domain;

import java.util.HashMap;
import java.util.Map;

public record Payload(
        Subject subject,
        Map<String, Object> claims
) {

    public Payload(Subject subject) {
        this(subject, new HashMap<>());
    }

    public <T> T get(String key, Class<T> type) {
        if (!claims.containsKey(key)) {
            throw new IllegalArgumentException("Claim not found: " + key);
        }
        Object value = claims.get(key);
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Claim type mismatch for key: " + key);
        }
        return type.cast(value);
    }
}
