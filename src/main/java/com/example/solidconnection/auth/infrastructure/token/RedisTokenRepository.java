package com.example.solidconnection.auth.infrastructure.token;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.auth.service.TokenRepository;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class RedisTokenRepository implements TokenRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public final Token save(Token token) {
        redisTemplate.opsForValue().set(
                token.getTokenKey(),
                token.getTokenValue(),
                token.getExpiredTime(),
                TimeUnit.MILLISECONDS
        );
        return token;
    }

    @Override
    public final Optional<Token> findBySubjectAndTokenType(Subject subject, TokenType tokenType) {
        String tokenKey = tokenType.addPrefix(subject.value());
        String foundTokenValue = redisTemplate.opsForValue().get(tokenKey);
        if (foundTokenValue == null || foundTokenValue.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(new Token(subject, foundTokenValue, tokenType));
    }

    @Override
    public final void deleteBySubjectAndTokenType(Subject subject, TokenType tokenType) {
        String tokenKey = tokenType.addPrefix(subject.value());
        redisTemplate.delete(tokenKey);
    }
}
