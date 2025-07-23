package com.example.solidconnection.auth.service;

import static com.example.solidconnection.common.exception.ErrorCode.SIGN_IN_FAILED;

import com.example.solidconnection.auth.dto.EmailSignInRequest;
import com.example.solidconnection.auth.dto.SignInResponse;
import com.example.solidconnection.common.exception.CustomException;
import com.example.solidconnection.siteuser.domain.AuthType;
import com.example.solidconnection.siteuser.domain.SiteUser;
import com.example.solidconnection.siteuser.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/*
 * 보안을 위해 이메일과 비밀번호 중 무엇이 틀렸는지 구체적으로 응답하지 않는다.
 * */
@Service
@RequiredArgsConstructor
public class EmailSignInService {

    private final SignInService signInService;
    private final SiteUserRepository siteUserRepository;
    private final PasswordEncoder passwordEncoder;

    public SignInResponse signIn(EmailSignInRequest signInRequest) {
        SiteUser siteUser = getEmailMatchingUserOrThrow(signInRequest.email());
        validatePassword(signInRequest.password(), siteUser.getPassword());
        return signInService.signIn(siteUser);
    }

    private SiteUser getEmailMatchingUserOrThrow(String email) {
        return siteUserRepository.findByEmailAndAuthType(email, AuthType.EMAIL)
                .orElseThrow(() -> new CustomException(SIGN_IN_FAILED, "이메일과 비밀번호를 확인해주세요."));
    }

    private void validatePassword(String rawPassword, String encodedPassword) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw new CustomException(SIGN_IN_FAILED, "이메일과 비밀번호를 확인해주세요.");
        }
    }
}
