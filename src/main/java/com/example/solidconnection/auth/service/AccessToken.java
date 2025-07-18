package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.TokenType;

public class AccessToken extends Token {

    public AccessToken(Subject subject, String token) {
        super(subject, token, TokenType.ACCESS);
    }

    public AccessToken(String subject, String token) {
        super(subject, token, TokenType.ACCESS);
    }

    public AccessToken(Token token) {
        super(token.subject, token.tokenValue, TokenType.ACCESS);
    }
}
