package com.example.solidconnection.auth.domain;

public class AccessToken extends Token {

    public AccessToken(String subject, String token) {
        this(new Subject(subject), token);
    }

    public AccessToken(Subject subject, String token) {
        super(subject, token, TokenType.ACCESS);
    }

    public AccessToken(Token token) {
        super(token.getPayload(), token.getTokenValue(), token.getTokenType());
    }
}
