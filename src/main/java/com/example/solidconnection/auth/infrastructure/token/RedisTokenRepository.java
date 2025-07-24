package com.example.solidconnection.auth.infrastructure.token;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.auth.service.TokenProvider;
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
    private final TokenProvider tokenProvider;

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
        String tokenKey = createKey(subject, tokenType);
        String foundTokenValue = redisTemplate.opsForValue().get(tokenKey);
        if (foundTokenValue == null || foundTokenValue.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(tokenProvider.parseToken(foundTokenValue, tokenType));
    }

    @Override
    public final void deleteBySubjectAndTokenType(Subject subject, TokenType tokenType) {
        String tokenKey = createKey(subject, tokenType);
        redisTemplate.delete(tokenKey);
    }

    private String createKey(Subject subject, TokenType tokenType) {
        return tokenType.addPrefix(subject.value());
    }
}
