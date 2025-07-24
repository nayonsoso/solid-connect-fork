package com.example.solidconnection.auth.service.signup;

import com.example.solidconnection.auth.domain.SignUpToken;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.Token;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.auth.service.TokenProvider;
import com.example.solidconnection.auth.service.TokenRepository;
import com.example.solidconnection.common.exception.CustomException;
import com.example.solidconnection.common.exception.ErrorCode;
import com.example.solidconnection.siteuser.domain.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SignUpTokenService {

    private final TokenProvider tokenProvider;
    private final TokenRepository tokenRepository;

    public SignUpToken generateAndSaveSignUpToken(String email, AuthType authType) {
        Subject subject = new Subject(email);
        String tokenValue = tokenProvider.generateTokenValue(subject, TokenType.SIGN_UP);
        SignUpToken signUpToken = new SignUpToken(subject, tokenValue, authType);
        tokenRepository.save(signUpToken);
        return signUpToken;
    }

    public SignUpToken parseSignUpToken(String tokenValue) {
        try {
            Token token = tokenProvider.parseToken(tokenValue, TokenType.SIGN_UP);
            return new SignUpToken(token);
        } catch (Exception e) {// 오류(예: 만료, 시그니처 불일치) 발생 시 잘못된 토큰으로 일괄 예외 처리
            throw new CustomException(ErrorCode.INVALID_SIGNUP_TOKEN);
        }
    }

    public void validateIssuedByServer(SignUpToken signUpToken) {
        tokenRepository.findBySubjectAndTokenType(signUpToken.getSubject(), TokenType.SIGN_UP)
                .orElseThrow(() -> new CustomException(ErrorCode.SIGN_UP_TOKEN_NOT_ISSUED_BY_SERVER));
    }
}
