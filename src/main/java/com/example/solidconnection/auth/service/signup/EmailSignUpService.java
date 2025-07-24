package com.example.solidconnection.auth.service.signup;

import com.example.solidconnection.auth.dto.EmailSignUpTokenRequest;
import com.example.solidconnection.auth.dto.EmailSignUpTokenResponse;
import com.example.solidconnection.common.exception.CustomException;
import com.example.solidconnection.common.exception.ErrorCode;
import com.example.solidconnection.siteuser.domain.AuthType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailSignUpService {

    private final SignUpTokenService signUpTokenService;
    private final PasswordTemporaryRepository passwordTemporaryRepository;

    public EmailSignUpTokenResponse issueSignUpToken(EmailSignUpTokenRequest signUpRequest) {
        String signUpToken = signUpTokenService.generateAndSaveSignUpToken(signUpRequest.email(), AuthType.EMAIL).getTokenValue();
        passwordTemporaryRepository.save(signUpRequest.email(), signUpRequest.password());
        return new EmailSignUpTokenResponse(signUpToken);
    }

    public String getTemporarySavedPassword(String email) {
        // 저장한 비밀번호가 없다는 것은 sign-up 토큰이 만료되었거나, 발급되지 않았다는 의미
        return passwordTemporaryRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_SIGNUP_TOKEN));
    }
}
