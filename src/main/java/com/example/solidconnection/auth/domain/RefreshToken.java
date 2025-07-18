package com.example.solidconnection.auth.domain;

public class RefreshToken extends AuthToken {

    public RefreshToken(Token token) {
        super(token.claims.subject(), token.tokenValue, TokenType.REFRESH);
    }
}
