package com.example.solidconnection.auth.service.oauth;

import static com.example.solidconnection.common.exception.ErrorCode.SIGN_UP_TOKEN_INVALID;
import static com.example.solidconnection.common.exception.ErrorCode.SIGN_UP_TOKEN_NOT_ISSUED_BY_SERVER;

import com.example.solidconnection.auth.domain.Payload;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.auth.service.TokenProvider;
import com.example.solidconnection.auth.service.TokenRepository;
import com.example.solidconnection.common.exception.CustomException;
import com.example.solidconnection.siteuser.domain.AuthType;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OAuthSignUpTokenProvider {

    static final String AUTH_TYPE_CLAIM_KEY = "authType";

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    public String generateAndSaveSignUpToken(String email, AuthType authType) {
        Token token = tokenProvider.generateToken(new Subject(email), TokenType.SIGN_UP);
        tokenRepository.save(token);
        return token.getTokenValue();
    }

    public void validateSignUpToken(String token) {
        validateFormatAndExpiration(token);
        String email = parseEmail(token);
        validateIssuedByServer(email);
    }

    private void validateFormatAndExpiration(String token) {
        try {
            Payload payload = tokenProvider.parsePayload(token);
            Objects.requireNonNull(payload);
            payload.get(AUTH_TYPE_CLAIM_KEY, AuthType.class);
        } catch (Exception e) {
            throw new CustomException(SIGN_UP_TOKEN_INVALID);
        }
    }

    private void validateIssuedByServer(String email) {
        tokenRepository.findBySubjectAndTokenType(new Subject(email), TokenType.SIGN_UP)
                .orElseThrow(() -> new CustomException(SIGN_UP_TOKEN_NOT_ISSUED_BY_SERVER));
    }

    public String parseEmail(String token) {
        return tokenProvider.parseSubject(token).value();
    }

    public AuthType parseAuthType(String token) {
        Payload payload = tokenProvider.parsePayload(token);
        return payload.get(AUTH_TYPE_CLAIM_KEY, AuthType.class);
    }
}
