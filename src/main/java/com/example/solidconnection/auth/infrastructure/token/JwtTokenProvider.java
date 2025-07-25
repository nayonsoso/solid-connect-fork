package com.example.solidconnection.auth.infrastructure.token;

import static com.example.solidconnection.common.exception.ErrorCode.INVALID_TOKEN;

import com.example.solidconnection.auth.domain.PrivateClaims;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenValue;
import com.example.solidconnection.auth.infrastructure.token.config.JwtProperties;
import com.example.solidconnection.auth.service.TokenProvider;
import com.example.solidconnection.common.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/*
 * jwt 토큰을 생성, 파싱하는 클래스
 * 이 클래스 안에서만 jwt 기술이 사용되고, 외부에서는 POJO만 사용될 수 있다.
 * */
@Component
@RequiredArgsConstructor
public class JwtTokenProvider implements TokenProvider {

    private final JwtProperties jwtProperties;

    @Override
    public final TokenValue generateTokenValue(Subject subject, long expireTime) {
        String tokenValue = generateJwtTokenValue(
                subject.value(), Map.of(), expireTime
        );
        return new TokenValue(tokenValue);
    }

    @Override
    public TokenValue generateTokenValue(Subject subject, PrivateClaims privateClaims, long expireTime) {
        String tokenValue = generateJwtTokenValue(
                subject.value(), privateClaims.claims(), expireTime
        );
        return new TokenValue(tokenValue);
    }

    private String generateJwtTokenValue(String subject, Map<String, String> claims, long expireTime) {
        Claims jwtClaims = Jwts.claims().setSubject(subject);
        jwtClaims.putAll(claims);
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireTime);
        return Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.secret())
                .compact();
    }

    @Override
    public <T extends Token> T parseToken(String tokenValue, Class<T> tokenType) {
        Token token = new Token(
                parseSubject(tokenValue),
                parsePrivateClaims(tokenValue),
                new TokenValue(tokenValue)
        );
        return tokenType.cast(token);
    }

    @Override
    public Subject parseSubject(String tokenValue) {
        String jwtSubject = parseJwtClaims(tokenValue).getSubject();
        return new Subject(jwtSubject);
    }

    public PrivateClaims parsePrivateClaims(String token) {
        Claims claims = parseJwtClaims(token);
        Map<String, String> privateClaimsMap = claims.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, e -> (String) e.getValue()));
        return new PrivateClaims(privateClaimsMap);
    }

    private Claims parseJwtClaims(String token) {
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
