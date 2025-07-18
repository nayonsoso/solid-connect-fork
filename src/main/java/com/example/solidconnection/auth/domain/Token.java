package com.example.solidconnection.auth.domain;

import lombok.Getter;

@Getter
public class Token {

    protected Payload payload;
    protected String tokenValue;
    protected TokenType tokenType;

    public Token(String subject, String tokenValue, TokenType tokenType) {
        this(new Subject(subject), tokenValue, tokenType);
    }

    public Token(Subject subject, String tokenValue, TokenType tokenType) {
        this(new Payload(subject), tokenValue, tokenType);
    }

    public Token(Payload payload, String tokenValue, TokenType tokenType) {
        this.payload = payload;
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
    }

    public String getTokenKey() {
        return tokenType.addPrefix(payload.subject().value());
    }

    public long getExpiredTime() {
        return tokenType.getExpireTime();
    }
}
