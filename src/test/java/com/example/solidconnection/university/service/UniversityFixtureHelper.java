package com.example.solidconnection.university.service;

import com.example.solidconnection.repositories.CountryRepository;
import com.example.solidconnection.repositories.RegionRepository;
import com.example.solidconnection.support.fixture.CountryFixture;
import com.example.solidconnection.support.fixture.RegionFixture;
import com.example.solidconnection.support.fixture.UniversityFixture;
import com.example.solidconnection.university.repository.LanguageRequirementRepository;
import com.example.solidconnection.university.repository.UniversityInfoForApplyRepository;
import com.example.solidconnection.university.repository.UniversityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniversityFixtureHelper {

    private final RegionRepository regionRepository;
    private final CountryRepository countryRepository;
    private final UniversityRepository universityRepository;
    private final UniversityInfoForApplyRepository universityInfoForApplyRepository;
    private final LanguageRequirementRepository languageRequirementRepository;

    public void 모든_대학_정보_생성() {
        regionRepository.saveAll(RegionFixture.모든_지역());
        countryRepository.saveAll(CountryFixture.모든_국가());
        universityRepository.saveAll(UniversityFixture.모든_대학());
        // 한가지 생각이 드는건, 일단 여기까지는 반드시 필요한 / 세팅되어야 할 정보이고, 테스트에서 꺼내보지 않을 것 같아 문제는 없다 생각하는데요

        //universityInfoForApplyRepository.saveAll(UniversityInfoForApplyFixture.모든_대학_지원_정보());
        // 요 부분에서 일괄 저장이 아니라, 하나씩 저장하게 한다면 해결될 수도 있을 것 같아요
    }
    // 그러고 변수로 관리하자는 말씀인건가요?? 아하!! 음 그럼 대학별로 함수가 생겨야하는데 그러면 그 대학
    // 만들 때 국가나 지역이나 이런 정보는 어떻게 가져올까요 넵

    // 아니면 어렵게 생각하지 않고, (잠시 저 따라와보시죠!)
    // 아니오 객체를 생성하고 저장해서 반환하는 함수를 만들자는 느낌입니다!

    // 변수로 관리하는건 다시 생각해도 정말 위험한 것 같아요 ㅎㅎ..
    // 음.. 여기서 Map<String, 대학>으로 관리하고있는 건 별론가요? 대충 느낌만 적어놓고 고민해보겠습니다
    // 아하! 생각을 좀 해볼게요!
}
