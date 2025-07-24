package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import java.util.Map;

public interface TokenProvider {

    String generateTokenValue(Subject subject, TokenType tokenType);

    String generateTokenValue(Subject subject, Map<String, Object> claims, TokenType tokenType);

    Token parseToken(String tokenValue, TokenType tokenType);

    Subject parseSubject(String tokenValue);
}
