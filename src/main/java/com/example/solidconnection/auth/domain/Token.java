package com.example.solidconnection.auth.domain;

import java.util.HashMap;
import java.util.Map;
import lombok.Getter;

@Getter
public class Token {

    protected Subject subject;
    protected Map<String, Object> claims;
    protected String tokenValue;
    protected TokenType tokenType;

    public Token(Subject subject, String tokenValue, TokenType tokenType) {
        this(subject, new HashMap<>(), tokenValue, tokenType);
    }

    public Token(Subject subject, Map<String, Object> claims, String tokenValue, TokenType tokenType) {
        this.subject = subject;
        this.claims = claims;
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
    }

    public String getTokenKey() {
        return tokenType.addPrefix(subject.value());
    }

    public long getExpiredTime() {
        return tokenType.getExpireTime();
    }

    public void putClaim(String key, Object value) {
        this.claims.put(key, value);
    }

    public <T> T getClaim(String key, Class<T> type) {
        if (!this.claims.containsKey(key)) {
            throw new IllegalArgumentException("Claim not found: " + key);
        }
        Object value = this.claims.get(key);
        if (!type.isInstance(value)) {
            throw new IllegalArgumentException("Claim type mismatch for key: " + key);
        }
        return type.cast(value);
    }
}
