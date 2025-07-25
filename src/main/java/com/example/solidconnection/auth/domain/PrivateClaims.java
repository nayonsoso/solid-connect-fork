package com.example.solidconnection.auth.domain;

import java.util.Map;

/*
 * 표준에 정의되어 있지 않은 claim을 private claim 이라고 한다.
 * https://datatracker.ietf.org/doc/html/rfc7519#section-4.3
 * */
public record PrivateClaims(
        Map<String, String> claims
) {

}
