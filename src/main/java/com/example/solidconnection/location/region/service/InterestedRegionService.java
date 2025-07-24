package com.example.solidconnection.location.region.service;

import com.example.solidconnection.location.region.domain.InterestedRegion;
import com.example.solidconnection.location.region.repository.InterestedRegionRepository;
import com.example.solidconnection.location.region.repository.RegionRepository;
import com.example.solidconnection.siteuser.domain.SiteUser;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestedRegionService {

    private final RegionRepository regionRepository;
    private final InterestedRegionRepository interestedRegionRepository;

    @Transactional
    public void saveInterestedRegion(List<String> interestedRegionNames, SiteUser siteUser) {
        List<InterestedRegion> interestedRegions = regionRepository.findByKoreanNames(interestedRegionNames)
                .stream()
                .map(region -> new InterestedRegion(siteUser, region))
                .toList();
        interestedRegionRepository.saveAll(interestedRegions);
    }
}
