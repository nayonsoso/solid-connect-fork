package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.TokenType;
import lombok.Getter;

@Getter
public class Token {

    protected final Subject subject;
    protected final String tokenValue;
    protected final TokenType tokenType;

    public Token(Subject subject, String tokenValue, TokenType tokenType) {
        this.subject = subject;
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
    }

    public String getTokenKey() {
        return tokenType.addPrefix(subject.value());
    }

    public long getExpiredTime() {
        return tokenType.getExpireTime();
    }
}
