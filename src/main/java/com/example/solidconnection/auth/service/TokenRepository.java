package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenValue;
import java.util.Optional;

public interface TokenRepository {

    <T extends Token> T save(T token);

    Optional<TokenValue> findTokenValueBySubjectAndKeyPrefix(Subject subject, String keyPrefix);

    void deleteBySubjectAndKeyPrefix(Subject subject, String keyPrefix);
}
