package com.example.solidconnection.auth.service.signup;

import com.example.solidconnection.auth.domain.PrivateClaims;
import com.example.solidconnection.auth.domain.SignUpToken;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.TokenValue;
import com.example.solidconnection.auth.domain.config.TokenProperties;
import com.example.solidconnection.auth.service.TokenProvider;
import com.example.solidconnection.auth.service.TokenRepository;
import com.example.solidconnection.common.exception.CustomException;
import com.example.solidconnection.common.exception.ErrorCode;
import com.example.solidconnection.siteuser.domain.AuthType;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpTokenService {

    private static final String AUTH_TYPE_CLAIM_KEY = "authType";

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;
    private final TokenProperties tokenProperties;

    public SignUpToken generateAndSaveSignUpToken(String email, AuthType authType) {
        Subject subject = new Subject(email);
        PrivateClaims privateClaims = new PrivateClaims(Map.of(AUTH_TYPE_CLAIM_KEY, authType.name()));
        TokenValue tokenValue = tokenProvider.generateTokenValue(
                subject, privateClaims, tokenProperties.signUpToken().expireTime()
        );
        SignUpToken signUpToken = new SignUpToken(subject, privateClaims, tokenValue);
        return tokenRepository.save(signUpToken);
    }

    public SignUpToken parseSignUpToken(String tokenValue) {
        try {
            return tokenProvider.parseToken(tokenValue, SignUpToken.class);
        } catch (Exception e) { // 오류(예: 만료, 시그니처 불일치) 발생 시 잘못된 토큰으로 일괄 예외 처리
            throw new CustomException(ErrorCode.INVALID_SIGNUP_TOKEN);
        }
    }

    public AuthType parseAuthType(SignUpToken signUpToken) {
        return AuthType.valueOf(signUpToken.getClaim(AUTH_TYPE_CLAIM_KEY));
    }

    public void validateIssuedByServer(SignUpToken signUpToken) {
        tokenRepository.findTokenValueBySubjectAndKeyPrefix(
                signUpToken.getSubject(), tokenProperties.signUpToken().storageKeyPrefix()
        ).orElseThrow(() -> new CustomException(ErrorCode.SIGN_UP_TOKEN_NOT_ISSUED_BY_SERVER));
    }
}
