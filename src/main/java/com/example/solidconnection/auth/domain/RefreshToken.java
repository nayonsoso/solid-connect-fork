package com.example.solidconnection.auth.domain;

public class RefreshToken extends Token {

    public RefreshToken(String subject, String token) {
        super(new Subject(subject), token, TokenType.REFRESH);
    }

    public RefreshToken(Token token) {
        super(token.subject, token.tokenValue, TokenType.REFRESH);
    }
}
