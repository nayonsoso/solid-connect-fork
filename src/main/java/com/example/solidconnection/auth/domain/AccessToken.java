package com.example.solidconnection.auth.domain;

public class AccessToken extends Token {

    public AccessToken(Subject subject, String tokenValue) {
        super(subject, tokenValue, TokenType.ACCESS);
    }
}
