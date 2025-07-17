package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.TokenType;

public class RefreshToken extends Token {

    public RefreshToken(String subject, String token) {
        super(new Subject(subject), token, TokenType.REFRESH);
    }
}
