package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.PrivateClaims;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenValue;

public interface TokenProvider {

    TokenValue generateTokenValue(Subject subject, long expireTime);

    TokenValue generateTokenValue(Subject subject, PrivateClaims privateClaims, long expireTime);

    <T extends Token> T parseToken(String tokenValue, Class<T> tokenType);

    Subject parseSubject(String tokenValue);
}
