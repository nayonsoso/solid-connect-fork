package com.example.solidconnection.auth.service;

import com.example.solidconnection.auth.domain.AccessToken;
import com.example.solidconnection.auth.domain.PrivateClaims;
import com.example.solidconnection.auth.domain.RefreshToken;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.TokenValue;
import com.example.solidconnection.auth.domain.config.TokenProperties;
import com.example.solidconnection.siteuser.domain.Role;
import com.example.solidconnection.siteuser.domain.SiteUser;
import java.util.Map;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AuthTokenService {

    private static final String ROLE_CLAIM_KEY = "role";

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final TokenProperties tokenProperties;

    public AccessToken generateAccessToken(Subject subject, Role role) {
        PrivateClaims privateClaims = new PrivateClaims(Map.of(ROLE_CLAIM_KEY, role.name()));
        TokenValue tokenValue = tokenProvider.generateTokenValue(
                subject, privateClaims, tokenProperties.accessToken().expireTime()
        );
        return new AccessToken(subject, privateClaims, tokenValue);
    }

    public RefreshToken generateAndSaveRefreshToken(Subject subject) {
        TokenValue tokenValue = tokenProvider.generateTokenValue(
                subject, tokenProperties.refreshToken().expireTime()
        );
        RefreshToken refreshToken = new RefreshToken(subject, tokenValue);
        return tokenRepository.save(refreshToken);
    }

    /*
     * 유효한 리프레시 토큰인지 확인한다.
     * - 요청된 토큰과 같은 subject 의 리프레시 토큰을 조회한다.
     * - 조회된 리프레시 토큰과 요청된 토큰이 같은지 비교한다.
     * */
    public boolean isValidRefreshToken(String requestedToken) {
        Subject subject = tokenProvider.parseSubject(requestedToken);
        Optional<TokenValue> optionalToken = tokenRepository.findTokenValueBySubjectAndKeyPrefix(
                subject, tokenProperties.refreshToken().storageKeyPrefix()
        );
        return optionalToken.isPresent() && optionalToken.get().value().equals(requestedToken);
    }

    public void deleteRefreshTokenByAccessToken(AccessToken accessToken) {
        Subject subject = accessToken.getSubject();
        tokenRepository.deleteBySubjectAndKeyPrefix(
                subject, tokenProperties.refreshToken().storageKeyPrefix()
        );
    }

    public Subject parseSubject(String token) {
        return tokenProvider.parseSubject(token);
    }

    public Subject parseSubject(SiteUser siteUser) {
        return new Subject(siteUser.getId().toString());
    }

    public AccessToken parseAccessToken(String token) {
        return tokenProvider.parseToken(token, AccessToken.class);
    }
}
