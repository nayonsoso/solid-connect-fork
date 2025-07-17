package com.example.solidconnection.auth.token;

import static com.example.solidconnection.common.exception.ErrorCode.INVALID_TOKEN;

import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.auth.service.Subject;
import com.example.solidconnection.auth.service.Token;
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
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + tokenType.getExpireTime());
        String token = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.secret())
                .compact();
        return new Token(subject, token, tokenType);
    }

    @Override
    public final Token saveToken(Token token) {
        redisTemplate.opsForValue().set(
                token.getTokenKey(),
                token.getToken(),
                token.getExpiredTime(),
                TimeUnit.MILLISECONDS
        );
        return token;
    }

    @Override
    public final Optional<String> findToken(String token, TokenType tokenType) {
        String subject = parseSubject(token);
        String tokenKey = tokenType.addPrefix(subject);
        return Optional.ofNullable(redisTemplate.opsForValue().get(tokenKey));
    }

    @Override
    public final void deleteToken(String token) {
        redisTemplate.delete(token);
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
