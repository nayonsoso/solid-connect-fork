package com.example.solidconnection.auth.infrastructure.token;

import static com.example.solidconnection.common.exception.ErrorCode.INVALID_TOKEN;

import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.auth.infrastructure.token.config.JwtProperties;
import com.example.solidconnection.auth.service.TokenProvider;
import com.example.solidconnection.common.exception.CustomException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Date;
import java.util.Map;
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
    public final String generateTokenValue(Subject subject, TokenType tokenType) {
        Claims jwtClaims = Jwts.claims().setSubject(subject.value());
        return generateJwtTokenValue(jwtClaims, tokenType);
    }

    @Override
    public String generateTokenValue(Subject subject, Map<String, Object> claims, TokenType tokenType) {
        Claims jwtClaims = Jwts.claims().setSubject(subject.value());
        jwtClaims.putAll(claims);
        return generateJwtTokenValue(jwtClaims, tokenType);
    }

    private String generateJwtTokenValue(Claims jwtClaims, TokenType tokenType) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + tokenType.getExpireTime());
        return Jwts.builder()
                .setClaims(jwtClaims)
                .setIssuedAt(now)
                .setExpiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.secret())
                .compact();
    }

    @Override
    public Token parseToken(String tokenValue, TokenType tokenType) {
        return new Token(parseSubject(tokenValue), parseJwtClaims(tokenValue), tokenValue, tokenType);
    }

    @Override
    public Subject parseSubject(String tokenValue) {
        String jwtSubject = parseJwtClaims(tokenValue).getSubject();
        return new Subject(jwtSubject);
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
