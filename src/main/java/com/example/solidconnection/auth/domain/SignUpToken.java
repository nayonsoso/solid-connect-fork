package com.example.solidconnection.auth.domain;

public class SignUpToken extends Token {

    public SignUpToken(
            Subject subject,
            PrivateClaims privateClaims,
            TokenValue tokenValue
    ) {
        super(subject, privateClaims, tokenValue);
    }

    public String getEmail() {
        return super.subject.value();
    }
}
