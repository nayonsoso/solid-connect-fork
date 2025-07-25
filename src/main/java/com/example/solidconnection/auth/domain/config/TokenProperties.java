package com.example.solidconnection.auth.domain.config;

import com.example.solidconnection.auth.domain.AccessToken;
import com.example.solidconnection.auth.domain.RefreshToken;
import com.example.solidconnection.auth.domain.SignUpToken;
import com.example.solidconnection.auth.domain.Token;
import jakarta.annotation.PostConstruct;
import java.util.Map;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "token")
public record TokenProperties(
        TokenConfig accessToken,
        TokenConfig refreshToken,
        TokenConfig signUpToken,
        TokenConfig blacklistToken
) {

    private static Map<Class<? extends Token>, TokenConfig> tokenConfigs;

    @PostConstruct
    public void init() {
        tokenConfigs = Map.of(
                AccessToken.class, accessToken,
                RefreshToken.class, refreshToken,
                SignUpToken.class, signUpToken
        );
    }

    public static long getExpireTime(Class<? extends Token> tokenClass) {
        return tokenConfigs.get(tokenClass).expireTime();
    }

    public static String getStorageKeyPrefix(Class<? extends Token> tokenClass) {
        return tokenConfigs.get(tokenClass).storageKeyPrefix();
    }
}
