package com.example.solidconnection.support.fixture;

import com.example.solidconnection.entity.Region;

import java.util.List;

public class RegionFixture {

    public static Region 아시아() {
        return new Region("ASIA", "아시아");
    }

    public static Region 영미권() {
        return new Region("AMERICAS", "영미권");
    }

    public static Region 유럽() {
        return new Region("EUROPE", "유럽");
    }

    public static List<Region> 모든_지역() {
        return List.of(아시아(), 영미권(), 유럽());
    }
}
