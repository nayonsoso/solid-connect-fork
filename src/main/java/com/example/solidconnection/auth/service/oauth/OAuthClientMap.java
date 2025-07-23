package com.example.solidconnection.auth.service.oauth;

import com.example.solidconnection.siteuser.domain.AuthType;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OAuthClientMap {

    @Bean
    public Map<AuthType, OAuthClient> oauthClientMap(List<OAuthClient> oauthClients) {
        return oauthClients.stream().collect(
                Collectors.toMap(OAuthClient::getAuthType, Function.identity())
        );
    }
}
