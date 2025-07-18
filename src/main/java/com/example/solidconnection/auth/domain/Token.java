package com.example.solidconnection.auth.domain;

import lombok.Getter;

@Getter
public class Token {

    protected Claims claims;
    protected String tokenValue;
    protected TokenType tokenType;

    public Token(Claims claims, String tokenValue, TokenType tokenType) {
        this.claims = claims;
        this.tokenValue = tokenValue;
        this.tokenType = tokenType;
    }

    public String getTokenKey() {
        return tokenType.addPrefix(claims.subject().value());
    }

    public long getExpiredTime() {
        return tokenType.getExpireTime();
    }
}
