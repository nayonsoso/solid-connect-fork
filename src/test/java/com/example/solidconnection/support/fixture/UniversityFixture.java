package com.example.solidconnection.support.fixture;

import com.example.solidconnection.university.domain.University;

import java.util.List;

public class UniversityFixture {

    public static University 영미권_미국_괌대학() {
        return new University(
                null, "괌대학", "University of Guam", "university_of_guam",
                "https://www.uog.edu/admissions/international-students",
                "https://www.uog.edu/admissions/course-schedule",
                "https://www.uog.edu/life-at-uog/residence-halls/",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/university_of_guam/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/university_of_guam/1.png",
                null, null, null
        );
    }

    public static University 영미권_미국_네바다주립대학_라스베이거스() {
        return new University(
                null, "네바다주립대학 라스베이거스", "University of Nevada, Las Vegas", "university_of_nevada_las_vegas",
                "https://www.unlv.edu/engineering/eip",
                "https://www.unlv.edu/engineering/academic-programs",
                "https://www.unlv.edu/housing",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/university_of_nevada_las_vegas/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/university_of_nevada_las_vegas/1.png",
                null, null, null
        );
    }

    public static University 영미권_캐나다_메모리얼대학_세인트존스() {
        return new University(
                null, "메모리얼 대학 세인트존스", "Memorial University of Newfoundland St. John's", "memorial_university_of_newfoundland_st_johns",
                "https://mun.ca/goabroad/visiting-students-inbound/",
                "https://www.unlv.edu/engineering/academic-programs",
                "https://www.mun.ca/residences/",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/memorial_university_of_newfoundland_st_johns/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/memorial_university_of_newfoundland_st_johns/1.png",
                null, null, null
        );
    }

    public static University 유럽_덴마크_서던덴마크대학교() {
        return new University(
                null, "서던덴마크대학교", "University of Southern Denmark", "university_of_southern_denmark",
                "https://www.sdu.dk/en",
                "https://www.sdu.dk/en",
                "https://www.sdu.dk/en/uddannelse/information_for_international_students/studenthousing",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/university_of_southern_denmark/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/university_of_southern_denmark/1.png",
                null, null, null
        );
    }

    public static University 유럽_덴마크_코펜하겐IT대학() {
        return new University(
                null, "코펜하겐 IT대학", "IT University of Copenhagen", "it_university_of_copenhagen",
                "https://en.itu.dk/", null,
                "https://en.itu.dk/Programmes/Student-Life/Practical-information-for-international-students",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/it_university_of_copenhagen/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/it_university_of_copenhagen/1.png",
                null, null, null
        );
    }

    public static University 유럽_오스트리아_그라츠대학() {
        return new University(
                null, "그라츠 대학", "University of Graz", "university_of_graz",
                "https://www.uni-graz.at/en/",
                "https://static.uni-graz.at/fileadmin/veranstaltungen/orientation/documents/incstud_application-courses.pdf",
                "https://orientation.uni-graz.at/de/planning-the-arrival/accommodation/",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/university_of_graz/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/university_of_graz/1.png",
                null, null, null
        );
    }

    public static University 유럽_오스트리아_그라츠공과대학() {
        return new University(
                null, "그라츠공과대학", "Graz University of Technology", "graz_university_of_technology",
                "https://www.tugraz.at/en/home", null,
                "https://www.tugraz.at/en/studying-and-teaching/studying-internationally/incoming-students-exchange-at-tu-graz/your-stay-at-tu-graz/preparation#c75033",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/graz_university_of_technology/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/graz_university_of_technology/1.png",
                null, null, null
        );
    }

    public static University 유럽_오스트리아_린츠_카톨릭대학() {
        return new University(
                null, "린츠 카톨릭 대학교", "Catholic Private University Linz", "catholic_private_university_linz",
                "https://ku-linz.at/en", null,
                "https://ku-linz.at/en/ku_international/incomings/kulis",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/catholic_private_university_linz/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/catholic_private_university_linz/1.png",
                null, null, null
        );
    }

    public static University 아시아_일본_메이지대학() {
        return new University(
                null, "메이지대학", "Meiji University", "meiji_university",
                "https://www.meiji.ac.jp/cip/english/admissions/co7mm90000000461-att/co7mm900000004fa.pdf", null,
                "https://www.meiji.ac.jp/cip/english/admissions/co7mm90000000461-att/co7mm900000004fa.pdf",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/meiji_university/logo.png",
                "https://solid-connection.s3.ap-northeast-2.amazonaws.com/original/meiji_university/1.png",
                null, null, null
        );
    }

    public static List<University> 모든_대학() {
        return List.of(
                영미권_미국_괌대학(), 영미권_미국_네바다주립대학_라스베이거스(),
                영미권_캐나다_메모리얼대학_세인트존스(),
                유럽_덴마크_서던덴마크대학교(), 유럽_덴마크_코펜하겐IT대학(),
                유럽_오스트리아_그라츠대학(), 유럽_오스트리아_그라츠공과대학(), 유럽_오스트리아_린츠_카톨릭대학(),
                아시아_일본_메이지대학()
        );
    }
}
