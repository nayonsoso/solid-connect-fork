package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.TokenType;
import io.jsonwebtoken.Claims;
import java.util.Optional;

public interface TokenProvider {

    Token generateToken(Subject subject, TokenType tokenType);

    Token saveToken(Token token);

    Optional<String> findToken(String token, TokenType tokenType);

    void deleteToken(String token);

    String parseSubject(String token);

    Claims parseClaims(String token);
}
