package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.TokenType;
import lombok.Getter;

@Getter
public class Token {

    protected final Subject subject;
    protected final String token;
    protected final TokenType tokenType;

    public Token(Subject subject, String token, TokenType tokenType) {
        this.subject = subject;
        this.token = token;
        this.tokenType = tokenType;
    }

    public String getTokenKey() {
        return tokenType.addPrefix(subject.value());
    }

    public long getExpiredTime() {
        return tokenType.getExpireTime();
    }
}
