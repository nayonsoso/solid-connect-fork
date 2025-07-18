package com.example.solidconnection.auth.token;

import static com.example.solidconnection.common.exception.ErrorCode.INVALID_TOKEN;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.auth.service.TokenProvider;
import com.example.solidconnection.auth.token.config.JwtProperties;
import com.example.solidconnection.common.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final JwtProperties jwtProperties;
    private final RedisTemplate<String, String> redisTemplate;

    @Override
    public final Token generateToken(Subject subject, TokenType tokenType) {
        Claims claims = Jwts.claims().setSubject(subject.value());
        return generateToken(claims, tokenType);
    }

    @Override
    public Token generateToken(Claims claims, TokenType tokenType) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + tokenType.getExpireTime());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.secret())
                .compact();
        return new Token(claims.getSubject(), token, tokenType);
    }

    @Override
    public final Token saveToken(Token token) {
        redisTemplate.opsForValue().set(
                token.getTokenKey(),
                token.getTokenValue(),
                token.getExpiredTime(),
                TimeUnit.MILLISECONDS
        );
        return token;
    }

    @Override
    public final Optional<Token> findByTokenTypeAndValue(TokenType tokenType, String tokenValue) {
        String subject = parseSubject(tokenValue);
        String tokenKey = tokenType.addPrefix(subject);
        String foundTokenValue = redisTemplate.opsForValue().get(tokenKey);
        if (foundTokenValue == null || foundTokenValue.isBlank() || !foundTokenValue.equals(tokenValue)) {
            return Optional.empty();
        }
        return Optional.of(new Token(subject, foundTokenValue, tokenType));
    }

    @Override
    public final void deleteByTokenKey(String tokenKey) {
        redisTemplate.delete(tokenKey);
    }

    @Override
    public String parseSubject(String token) {
        return parseClaims(token).getSubject();
    }

    @Override
    public Claims parseClaims(String token) {
        try {
            return Jwts.parser()
                    .setSigningKey(jwtProperties.secret())
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            throw new CustomException(INVALID_TOKEN);
        }
    }
}
