package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import io.jsonwebtoken.Claims;
import java.util.Optional;

public interface TokenProvider {

    Token generateToken(Subject subject, TokenType tokenType);

    Token generateToken(Claims claims, TokenType tokenType);

    Token saveToken(Token token);

    Optional<Token> findByTokenTypeAndValue(TokenType tokenType, String tokenValue);

    void deleteByTokenKey(String token);

    String parseSubject(String token);

    Claims parseClaims(String token);
}
