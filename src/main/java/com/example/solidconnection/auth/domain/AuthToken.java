package com.example.solidconnection.auth.domain;

public class AuthToken extends Token {

    protected Subject subject;

    public AuthToken(Subject subject, String tokenValue, TokenType tokenType) {
        super(new Claims(subject, null), tokenValue, tokenType);
        this.subject = subject;
    }

    public AuthToken(String subject, String tokenValue, TokenType tokenType) {
        super(new Claims(new Subject(subject), null), tokenValue, tokenType);
        this.subject = this.claims.subject();
    }
}
