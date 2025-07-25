package com.example.solidconnection.auth.infrastructure.token;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenValue;
import com.example.solidconnection.auth.domain.config.TokenProperties;
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
    public final <T extends Token> T save(T token) {
        Class<? extends Token> tokenClass = token.getClass();
        String storageKeyPrefix = TokenProperties.getStorageKeyPrefix(tokenClass);
        String key = createKey(token.getSubject(), storageKeyPrefix);

        redisTemplate.opsForValue().set(
                key,
                token.getTokenValue(),
                TokenProperties.getExpireTime(tokenClass),
                TimeUnit.MILLISECONDS
        );
        return token;
    }

    @Override
    public final Optional<TokenValue> findTokenValueBySubjectAndKeyPrefix(Subject subject, String keyPrefix) {
        String key = createKey(subject, keyPrefix);
        String foundTokenValue = redisTemplate.opsForValue().get(key);
        if (foundTokenValue == null || foundTokenValue.isBlank()) {
            return Optional.empty();
        }
        return Optional.of(new TokenValue(foundTokenValue));
    }

    @Override
    public final void deleteBySubjectAndKeyPrefix(Subject subject, String keyPrefix) {
        String key = createKey(subject, keyPrefix);
        redisTemplate.delete(key);
    }

    private String createKey(Subject subject, String keyPrefix) {
        return keyPrefix + subject.value();
    }
}
