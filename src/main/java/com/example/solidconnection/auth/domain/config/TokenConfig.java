package com.example.solidconnection.auth.domain.config;

public record TokenConfig(
        String storageKeyPrefix,
        long expireTime
) {

}
