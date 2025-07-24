package com.example.solidconnection.location.country.service;

import com.example.solidconnection.location.country.domain.InterestedCountry;
import com.example.solidconnection.location.country.repository.CountryRepository;
import com.example.solidconnection.location.country.repository.InterestedCountryRepository;
import com.example.solidconnection.siteuser.domain.SiteUser;
import jakarta.transaction.Transactional;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InterestedCountryService {

    private final CountryRepository countryRepository;
    private final InterestedCountryRepository interestedCountryRepository;

    @Transactional
    public void saveInterestedCountry(List<String> interestedCountryNames, SiteUser siteUser) {
        List<InterestedCountry> interestedCountries = countryRepository.findByKoreanNames(interestedCountryNames)
                .stream()
                .map(country -> new InterestedCountry(siteUser, country))
                .toList();
        interestedCountryRepository.saveAll(interestedCountries);
    }
}
