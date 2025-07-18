package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.Payload;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;

public interface TokenProvider {

    Token generateToken(Subject subject, TokenType tokenType);

    Token generateToken(Payload payload, TokenType tokenType);

    Subject parseSubject(String token);

    Payload parsePayload(String token);
}
