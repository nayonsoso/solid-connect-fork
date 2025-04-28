package com.example.solidconnection.support.fixture;

import com.example.solidconnection.entity.Country;

import java.util.List;

public class CountryFixture {

    public static Country 미국() {
        return new Country("US", "미국", RegionFixture.영미권());
    }

    public static Country 캐나다() {
        return new Country("CA", "캐나다", RegionFixture.영미권());
    }

    public static Country 덴마크() {
        return new Country("DK", "덴마크", RegionFixture.유럽());
    }

    public static Country 오스트리아() {
        return new Country("AT", "오스트리아", RegionFixture.유럽());
    }

    public static Country 일본() {
        return new Country("JP", "일본", RegionFixture.아시아());
    }

    public static Country 한국() {
        // 여기에서 '아시아()'로 생성된 객체가 저장되지 않겠네요
        return new Country("KR", "한국", RegionFixture.아시아());
    }

    public static List<Country> 모든_국가() {
        return List.of(미국(), 캐나다(), 덴마크(), 오스트리아(), 일본(), 한국());
    }
}
