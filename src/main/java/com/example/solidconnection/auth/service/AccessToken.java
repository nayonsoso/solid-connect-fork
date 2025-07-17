package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.TokenType;

public class AccessToken extends Token {

    public AccessToken(String subject, String token) {
        super(new Subject(subject), token, TokenType.ACCESS);
    }

    public AccessToken(Token token) {
        super(token.subject, token.token, TokenType.ACCESS);
    }
}
