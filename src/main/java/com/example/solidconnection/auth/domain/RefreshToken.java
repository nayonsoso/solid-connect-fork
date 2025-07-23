package com.example.solidconnection.auth.domain;

public class RefreshToken extends Token {

    public RefreshToken(Subject subject, String tokenValue) {
        super(subject, tokenValue, TokenType.REFRESH);
    }
}
