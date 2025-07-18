package com.example.solidconnection.auth.domain;

public class RefreshToken extends Token {

    public RefreshToken(Token token) {
        super(token.getPayload(), token.getTokenValue(), token.getTokenType());
    }
}
