package com.example.solidconnection.support.fixture;

import com.example.solidconnection.custom.exception.CustomException;
import com.example.solidconnection.entity.Country;
import com.example.solidconnection.entity.Region;
import com.example.solidconnection.type.LanguageTestType;
import com.example.solidconnection.type.SemesterAvailableForDispatch;
import com.example.solidconnection.type.TuitionFeeType;
import com.example.solidconnection.university.domain.LanguageRequirement;
import com.example.solidconnection.university.domain.University;
import com.example.solidconnection.university.domain.UniversityInfoForApply;

import java.util.HashSet;
import java.util.Set;

import static com.example.solidconnection.custom.exception.ErrorCode.COUNTRY_NOT_FOUND_BY_KOREAN_NAME;
import static com.example.solidconnection.custom.exception.ErrorCode.REGION_NOT_FOUND_BY_KOREAN_NAME;
import static com.example.solidconnection.custom.exception.ErrorCode.UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND;

// 제가 UniversityFixture 만들면서 덮어씌워졌나봅니다....
// 일단 규혁님이 생각하시는대로 코드 작성해보실래요?
// 네 그럼 지금은 그냥 한 번에 다 생성하는 거만 만드는거죠?
// 아까 모든_대학_생성() 이거 관련해서 하는건가요 아니면 그냥 대학 하나 생성하는건가요?
// 음... 일단 하나만...? 해볼까요...?
// 제가 생각했을 때 베스트는, 선택적으로 한 대학만 할수도 있고, 아니면 한번에 세팅할 수도 있게 하고 싶었었거든요?
// 아ㅏㅎ 그럼 우리가 목표로하는 서비스코드를 바꾸려면 전체 다 해야겠네요!
// 네! 떨리네요 ㅎㅎ..
///  파이팅~~~~


// 한번에 다 생성한다는게 하는
public class UniversityFixtureGH {

    private final BuilderSupporter bs;

    private Region region;
    private Country country;
    private University university;
    private UniversityInfoForApply universityInfoForApply;
    private Set<LanguageRequirement> languageRequirements = new HashSet<>();

    public UniversityFixtureGH(BuilderSupporter bs) {
        this.bs = bs;
    }

    public UniversityFixtureGH 지역을_생성한다(String code, String koreanName) {
        Region region = new Region(code, koreanName);
        this.region = bs.regionRepository().save(region);
        return this;
    }

    public UniversityFixtureGH 국가를_생성한다(String code, String koreanName) {
        if (this.region == null) {
            throw new CustomException(REGION_NOT_FOUND_BY_KOREAN_NAME);
        }
        Country country = new Country(code, koreanName, this.region);
        this.country = bs.countryRepository().save(country);
        return this;
    }

    public UniversityFixtureGH 대학을_생성한다(
            String koreanName,
            String englishName,
            String formatName,
            String homepageUrl,
            String englishCourseUrl,
            String accommodationUrl,
            String logoImageUrl,
            String backgroundImageUrl,
            String detailsForLocal) {
        if (this.country == null) {
            throw new CustomException(COUNTRY_NOT_FOUND_BY_KOREAN_NAME);
        }
        University university = new University(
                null,
                koreanName,
                englishName,
                formatName,
                homepageUrl,
                englishCourseUrl,
                accommodationUrl,
                logoImageUrl,
                backgroundImageUrl,
                detailsForLocal,
                this.country,
                this.region);
        this.university = bs.universityRepository().save(university);
        return this;
    }

    public UniversityFixtureGH 대학_지원_정보를_생성한다(
            String term,
            String koreanName,
            Integer studentCapacity,
            TuitionFeeType tuitionFeeType,
            SemesterAvailableForDispatch semesterAvailableForDispatch,
            String semesterRequirement,
            String detailsForLanguage,
            String gpaRequirement,
            String gpaRequirementCriteria,
            String detailsForApply,
            String detailsForMajor,
            String detailsForAccommodation,
            String detailsForEnglishCourse,
            String details) {
        if (this.university == null) {
            throw new CustomException(UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND);
        }
        UniversityInfoForApply universityInfoForApply = new UniversityInfoForApply(
                null,
                term,
                koreanName,
                studentCapacity,
                tuitionFeeType,
                semesterAvailableForDispatch,
                semesterRequirement,
                detailsForLanguage,
                gpaRequirement,
                gpaRequirementCriteria,
                detailsForApply,
                detailsForMajor,
                detailsForAccommodation,
                detailsForEnglishCourse,
                details,
                new HashSet<>(),
                this.university);
        this.universityInfoForApply = bs.universityInfoForApplyRepository().save(universityInfoForApply);
        return this;
    }

    public UniversityFixtureGH 언어_요구사항을_추가한다(LanguageTestType testType, String minScore) {
        if (this.universityInfoForApply == null) {
            throw new CustomException(UNIVERSITY_INFO_FOR_APPLY_NOT_FOUND);
        }
        LanguageRequirement languageRequirement = new LanguageRequirement(
                null,
                testType,
                minScore,
                this.universityInfoForApply);
        this.universityInfoForApply.addLanguageRequirements(languageRequirement);
        bs.universityInfoForApplyRepository().save(this.universityInfoForApply);
        LanguageRequirement saved = bs.languageRequirementRepository().save(languageRequirement);
        this.languageRequirements.add(saved);
        return this;
    }

    public Region 지역() {
        return region;
    }

    public Country 국가() {
        return country;
    }

    public University 대학() {
        return university;
    }

    public UniversityInfoForApply 대학_지원_정보() {
        return universityInfoForApply;
    }

    public Set<LanguageRequirement> 언어_요구사항들() {
        return languageRequirements;
    }
}
