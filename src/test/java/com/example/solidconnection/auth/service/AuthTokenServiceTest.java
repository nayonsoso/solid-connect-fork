package com.example.solidconnection.auth.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.example.solidconnection.auth.domain.AccessToken;
import com.example.solidconnection.auth.domain.RefreshToken;
import com.example.solidconnection.auth.domain.Subject;
import com.example.solidconnection.auth.domain.TokenType;
import com.example.solidconnection.support.TestContainerSpringBootTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

@TestContainerSpringBootTest
@DisplayName("인증 토큰 제공자 테스트")
class AuthTokenServiceTest {

    @Autowired
    private AuthTokenService authTokenService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    private Subject subject;

    @BeforeEach
    void setUp() {
        subject = new Subject("subject123");
    }

    @Test
    void 액세스_토큰을_생성한다() {
        // when
        AccessToken accessToken = authTokenService.generateAccessToken(subject);

        // then
        String actualSubject = authTokenService.parseSubject(accessToken.token()).value();
        assertThat(actualSubject).isEqualTo(subject.value());
    }

    @Nested
    class 리프레시_토큰을_제공한다 {

        @Test
        void 리프레시_토큰을_생성하고_저장한다() {
            // when
            RefreshToken actualRefreshToken = authTokenService.generateAndSaveRefreshToken(subject);

            // then
            String actualSubject = authTokenService.parseSubject(actualRefreshToken.token()).value();
            String refreshTokenKey = TokenType.REFRESH.addPrefix(subject.value());
            String expectedRefreshToken = redisTemplate.opsForValue().get(refreshTokenKey);
            assertAll(
                    () -> assertThat(actualSubject).isEqualTo(subject.value()),
                    () -> assertThat(actualRefreshToken.token()).isEqualTo(expectedRefreshToken)
            );
        }

        @Test
        void 유효한_리프레시_토큰인지_확인한다() {
            // given
            RefreshToken refreshToken = authTokenService.generateAndSaveRefreshToken(subject);
            AccessToken fakeRefreshToken = authTokenService.generateAccessToken(subject);

            // when, then
            assertAll(
                    () -> assertThat(authTokenService.isValidRefreshToken(refreshToken.token())).isTrue(),
                    () -> assertThat(authTokenService.isValidRefreshToken(fakeRefreshToken.token())).isFalse()
            );
        }

        @Test
        void 액세스_토큰에_해당하는_리프레시_토큰을_삭제한다() {
            // given
            authTokenService.generateAndSaveRefreshToken(subject);
            AccessToken accessToken = authTokenService.generateAccessToken(subject);

            // when
            authTokenService.deleteRefreshTokenByAccessToken(accessToken);

            // then
            String refreshTokenKey = TokenType.REFRESH.addPrefix(subject.value());
            assertThat(redisTemplate.opsForValue().get(refreshTokenKey)).isNull();
        }
    }

    @Test
    void 토큰으로부터_Subject_를_추출한다() {
        // given
        String accessToken = authTokenService.generateAccessToken(subject).token();

        // when
        Subject actualSubject = authTokenService.parseSubject(accessToken);

        // then
        assertThat(actualSubject.value()).isEqualTo(subject.value());
    }
}
