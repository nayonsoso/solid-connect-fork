package com.example.solidconnection.auth.domain;

import java.util.Map;
import lombok.Getter;

@Getter
public class Token {

    protected Subject subject;
    protected PrivateClaims privateClaims;
    protected TokenValue tokenValue;

    public Token(
            Subject subject,
            TokenValue tokenValue
    ) {
        this(subject, new PrivateClaims(Map.of()), tokenValue);
    }

    public Token(
            Subject subject,
            PrivateClaims privateClaims,
            TokenValue tokenValue
    ) {
        this.subject = subject;
        this.privateClaims = privateClaims;
        this.tokenValue = tokenValue;
    }

    public String getTokenValue() {
        return tokenValue.value();
    }

    public String getClaim(String key) {
        if (!privateClaims.claims().containsKey(key)) {
            throw new IllegalArgumentException("Claim not found: " + key);
        }
        return this.privateClaims.claims().get(key);
    }
}
