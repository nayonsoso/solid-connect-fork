package com.example.solidconnection.auth.domain;

public class AccessToken extends AuthToken {

    public AccessToken(Subject subject, String token) {
        super(subject, token, TokenType.ACCESS);
    }

    public AccessToken(String subject, String token) {
        super(subject, token, TokenType.ACCESS);
    }

    public AccessToken(Token token) {
        super(token.claims.subject(), token.tokenValue, TokenType.ACCESS);
    }
}
