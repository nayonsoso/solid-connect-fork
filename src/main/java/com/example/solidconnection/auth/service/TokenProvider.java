package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.TokenType;
import io.jsonwebtoken.Claims;
import java.util.Optional;

public interface TokenProvider {

    String generateToken(String string, TokenType tokenType);

    String saveToken(String token, TokenType tokenType);

    Optional<String> findToken(String token, TokenType tokenType);

    void deleteToken(String token);

    String parseSubject(String token);

    Claims parseClaims(String token);
}
