package com.example.solidconnection.auth.service.signup;

import com.example.solidconnection.auth.domain.TokenType;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PasswordTemporaryRepository {

    private static final String KEY_PREFIX = "password:";
    private static final long EXPIRE_TIME = TokenType.SIGN_UP.getExpireTime();

    private final RedisTemplate<String, String> redisTemplate;
    private final PasswordEncoder passwordEncoder;

    public void save(String email, String password) {
        String encodedPassword = passwordEncoder.encode(password);
        redisTemplate.opsForValue()
                .set(createKey(email), encodedPassword, EXPIRE_TIME);
    }

    public Optional<String> findByEmail(String email) {
        String encodedPassword = redisTemplate.opsForValue().get(createKey(email));
        if (encodedPassword == null) {
            return Optional.empty();
        }
        return Optional.of(encodedPassword);
    }

    private String createKey(String email) {
        return KEY_PREFIX + email;
    }
}
