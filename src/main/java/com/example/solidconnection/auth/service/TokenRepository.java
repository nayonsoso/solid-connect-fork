package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import java.util.Optional;

public interface TokenRepository {

    Token save(Token token);

    Optional<Token> findBySubjectAndTokenType(Subject subject, TokenType tokenType);

    void deleteBySubjectAndTokenType(Subject subject, TokenType tokenType);
}
