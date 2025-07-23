package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.AccessToken;
import com.example.solidconnection.auth.domain.RefreshToken;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.siteuser.domain.SiteUser;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenService {

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    public AccessToken generateAccessToken(Subject subject) {
        Token token = tokenProvider.generateToken(subject, TokenType.ACCESS);
        return new AccessToken(token);
    }

    public RefreshToken generateAndSaveRefreshToken(Subject subject) {
        Token token = tokenProvider.generateToken(subject, TokenType.REFRESH);
        tokenRepository.save(token);
        return new RefreshToken(token);
    }

    /*
     * 유효한 리프레시 토큰인지 확인한다.
     * - 요청된 토큰과 같은 subject 의 리프레시 토큰을 조회한다.
     * - 조회된 리프레시 토큰과 요청된 토큰이 같은지 비교한다.
     * */
    public boolean isValidRefreshToken(String requestedToken) {
        Subject subject = tokenProvider.parseSubject(requestedToken);
        Optional<Token> optionalToken = tokenRepository.findBySubjectAndTokenType(subject, TokenType.REFRESH);
        return optionalToken.isPresent() && optionalToken.get().getTokenValue().equals(requestedToken);
    }

    public void deleteRefreshTokenByAccessToken(AccessToken accessToken) {
        Subject subject = accessToken.getPayload().subject();
        tokenRepository.deleteBySubjectAndTokenType(subject, TokenType.REFRESH);
    }

    public Subject parseSubject(String token) {
        return tokenProvider.parseSubject(token);
    }

    public Subject parseSubject(SiteUser siteUser) {
        return new Subject(siteUser.getId().toString());
    }

    public AccessToken parseAccessToken(String token) {
        return new AccessToken(parseSubject(token), token);
    }
}
