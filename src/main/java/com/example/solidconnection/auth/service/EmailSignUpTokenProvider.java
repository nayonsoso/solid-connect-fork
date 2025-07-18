package com.example.solidconnection.auth.service;

import static com.example.solidconnection.common.exception.ErrorCode.SIGN_UP_TOKEN_INVALID;
import static com.example.solidconnection.common.exception.ErrorCode.SIGN_UP_TOKEN_NOT_ISSUED_BY_SERVER;

import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.auth.dto.EmailSignUpTokenRequest;
import com.example.solidconnection.common.exception.CustomException;
import com.example.solidconnection.siteuser.domain.AuthType;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailSignUpTokenProvider {

    static final String PASSWORD_CLAIM_KEY = "password";
    static final String AUTH_TYPE_CLAIM_KEY = "authType";

    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;

    public String generateAndSaveSignUpToken(EmailSignUpTokenRequest request) {
        String email = request.email();
        String password = request.password();
        String encodedPassword = passwordEncoder.encode(password);
        Map<String, Object> emailSignUpClaims = new HashMap<>(Map.of(
                PASSWORD_CLAIM_KEY, encodedPassword,
                AUTH_TYPE_CLAIM_KEY, AuthType.EMAIL
        )); // todo: 비밀번호를 넘기는게 정상이니?

        // todo: Claims 자체가 없었으면 좋겠다...
        Claims claims = Jwts.claims(emailSignUpClaims).setSubject(email);

        Token generateToken = tokenProvider.generateToken(claims, TokenType.SIGN_UP);
        return tokenProvider.saveToken(generateToken).getTokenValue();
    }

    public void validateSignUpToken(String token) {
        validateFormatAndExpiration(token);
        validateIssuedByServer(token);
    }

    private void validateFormatAndExpiration(String token) {
        try {
            Claims claims = tokenProvider.parseClaims(token);
            Objects.requireNonNull(claims.getSubject());
            String encodedPassword = claims.get(PASSWORD_CLAIM_KEY, String.class);
            Objects.requireNonNull(encodedPassword);
        } catch (Exception e) {
            throw new CustomException(SIGN_UP_TOKEN_INVALID);
        }
    }

    private void validateIssuedByServer(String token) {
        tokenProvider.findByTokenTypeAndValue(TokenType.SIGN_UP, token)
                .orElseThrow(() -> new CustomException(SIGN_UP_TOKEN_NOT_ISSUED_BY_SERVER));
    }

    public String parseEmail(String token) {
        return tokenProvider.parseSubject(token);
    }

    public String parseEncodedPassword(String token) {
        Claims claims = tokenProvider.parseClaims(token);
        return claims.get(PASSWORD_CLAIM_KEY, String.class);
    }
}
