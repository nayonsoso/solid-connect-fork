package com.example.solidconnection.auth.domain;

import com.example.solidconnection.siteuser.domain.AuthType;

public class SignUpToken extends Token {

    private static final String AUTH_TYPE_CLAIM_KEY = "authType";

    public SignUpToken(Subject subject, String tokenValue, AuthType authType) {
        super(subject, tokenValue, TokenType.SIGN_UP);
        super.putClaim(AUTH_TYPE_CLAIM_KEY, authType);
    }

    public SignUpToken(Token token) {
        super(token.subject, token.claims, token.tokenValue, TokenType.SIGN_UP);
    }

    public String getEmail() {
        return super.subject.value();
    }

    public AuthType getAuthType() {
        return super.getClaim(AUTH_TYPE_CLAIM_KEY, AuthType.class);
    }
}
