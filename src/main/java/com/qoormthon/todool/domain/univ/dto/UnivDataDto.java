package com.qoormthon.todool.domain.univ.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class UnivDataDto {
    private List<School> records;

    @AllArgsConstructor
    @NoArgsConstructor
    @Getter
    @Setter
    public static class School {

        @JsonAlias("학교명")
        private String univName;

//        @JsonAlias("학교 영문명")
//        private String univNameEng;

//
        @JsonAlias("대학구분명")
        private String univTypeName;
//        private String 학교구분명;
//        private String 설립형태구분명;
//        private String 시도코드;
//        private String 시도명;
//        private String 소재지도로명주소;
//        private String 소재지지번주소;
//        private String 도로명우편번호;
//        private String 소재지우편번호;
        @JsonAlias("홈페이지주소")
        private String websiteUrl;
//        private String 대표전화번호;
//        private String 대표팩스번호;
//        private String 설립일자;
//        private String 기준연도;
//        private String 데이터기준일자;
//        private String 제공기관코드;
//        private String 제공기관명;
    }
}
