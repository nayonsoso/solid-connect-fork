package com.example.solidconnection.auth.domain;

public class AccessToken extends Token {

    public AccessToken(
            Subject subject,
            PrivateClaims privateClaims,
            TokenValue tokenValue
    ) {
        super(subject, privateClaims, tokenValue);
    }
}
