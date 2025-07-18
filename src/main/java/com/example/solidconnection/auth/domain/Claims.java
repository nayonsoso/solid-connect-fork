package com.example.solidconnection.auth.domain;

import java.util.Map;

public record Claims(
        Subject subject,
        Map<String, Object> additionalClaims
) {

}
