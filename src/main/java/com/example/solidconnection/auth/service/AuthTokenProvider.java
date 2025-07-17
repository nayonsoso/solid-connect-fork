package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.siteuser.domain.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenProvider {

    private final TokenProvider tokenProvider;

    public AccessToken generateAccessToken(Subject subject) {
        Token token = tokenProvider.generateToken(subject, TokenType.ACCESS);
        return new AccessToken(token);
    }

    public RefreshToken generateAndSaveRefreshToken(Subject subject) {
        Token token = tokenProvider.generateToken(subject, TokenType.REFRESH);
        tokenProvider.saveToken(token);
        return new RefreshToken(token);
    }

    /*
     * 유효한 리프레시 토큰인지 확인한다.
     * - 요청된 토큰과 같은 subject 의 리프레시 토큰을 조회한다.
     * - 조회된 리프레시 토큰과 요청된 토큰이 같은지 비교한다.
     * */
    public boolean isValidRefreshToken(String requestedToken) {
        return tokenProvider.findByTokenTypeAndValue(TokenType.REFRESH, requestedToken).isPresent();
    }

    public void deleteRefreshTokenByAccessToken(AccessToken accessToken) {
        String subject = accessToken.subject().value();
        String refreshTokenKey = TokenType.REFRESH.addPrefix(subject);
        tokenProvider.deleteToken(refreshTokenKey);
    }

    public Subject parseSubject(String token) {
        String subject = tokenProvider.parseSubject(token);
        return new Subject(subject);
    }

    public Subject generateSubject(SiteUser siteUser) {
        return new Subject(siteUser.getId().toString());
    }

    public AccessToken generateAccessToken(String token) {
        return new AccessToken(parseSubject(token), token);
    }
}
