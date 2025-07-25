package com.example.solidconnection.auth.service.signup;

import static com.example.solidconnection.common.exception.ErrorCode.NICKNAME_ALREADY_EXISTED;
import static com.example.solidconnection.common.exception.ErrorCode.USER_ALREADY_EXISTED;

import com.example.solidconnection.auth.domain.SignUpToken;
import com.example.solidconnection.auth.dto.SignInResponse;
import com.example.solidconnection.auth.dto.SignUpRequest;
import com.example.solidconnection.auth.service.SignInService;
import com.example.solidconnection.common.exception.CustomException;
import com.example.solidconnection.location.country.service.InterestedCountryService;
import com.example.solidconnection.location.region.service.InterestedRegionService;
import com.example.solidconnection.siteuser.domain.AuthType;
import com.example.solidconnection.siteuser.domain.Role;
import com.example.solidconnection.siteuser.domain.SiteUser;
import com.example.solidconnection.siteuser.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/*
 * - 우리 서버에서 인증되었음을 확인하기 위한 signUpToken 을 검증한다.
 * - 사용자 정보를 DB에 저장한다.
 * - 관심 국가와 지역을 DB에 저장한다.
 *   - 관심 국가와 지역은 site_user_id를 참조하므로, 사용자 저장 후 저장한다.
 * - 바로 로그인하도록 액세스 토큰과 리프레시 토큰을 발급한다.
 * */
@Service
@RequiredArgsConstructor
public class SignUpService {

    private final SignUpTokenService signUpTokenService;
    private final EmailSignUpService emailSignUpService;
    private final SignInService signInService;
    private final SiteUserRepository siteUserRepository;
    private final InterestedRegionService interestedRegionService;
    private final InterestedCountryService interestedCountryService;

    @Transactional
    public SignInResponse signUp(SignUpRequest signUpRequest) {
        // 검증
        SignUpToken signUpToken = signUpTokenService.parseSignUpToken(signUpRequest.signUpToken());
        AuthType authType = signUpTokenService.parseAuthType(signUpToken);
        signUpTokenService.validateIssuedByServer(signUpToken);
        validateUserNotDuplicated(signUpToken.getEmail(), authType);
        validateNicknameDuplicated(signUpRequest.nickname());

        // 임시 저장된 비밀번호 가져오기
        String password = getTemporarySavedPassword(signUpToken.getEmail(), authType);

        // 사용자 저장
        SiteUser siteUser = siteUserRepository.save(new SiteUser(
                signUpToken.getEmail(),
                signUpRequest.nickname(),
                signUpRequest.profileImageUrl(),
                signUpRequest.exchangeStatus(),
                Role.MENTEE,
                authType,
                password
        ));

        // 관심 지역, 국가 저장
        interestedRegionService.saveInterestedRegion(signUpRequest.interestedRegions(), siteUser);
        interestedCountryService.saveInterestedCountry(signUpRequest.interestedCountries(), siteUser);

        // 로그인
        return signInService.signIn(siteUser);
    }

    private void validateUserNotDuplicated(String email, AuthType authType) {
        if (siteUserRepository.existsByEmailAndAuthType(email, authType)) {
            throw new CustomException(USER_ALREADY_EXISTED);
        }
    }

    private void validateNicknameDuplicated(String nickname) {
        if (siteUserRepository.existsByNickname(nickname)) {
            throw new CustomException(NICKNAME_ALREADY_EXISTED);
        }
    }

    private String getTemporarySavedPassword(String email, AuthType authType) {
        if (authType == AuthType.EMAIL) {
            return emailSignUpService.getTemporarySavedPassword(email);
        }
        return null;
    }
}
