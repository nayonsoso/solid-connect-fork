package com.example.solidconnection.auth.domain;

public class RefreshToken extends Token {

    public RefreshToken(
            Subject subject,
            TokenValue tokenValue
    ) {
        super(subject, tokenValue);
    }
}
